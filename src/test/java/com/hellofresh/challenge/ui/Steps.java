package com.hellofresh.challenge.ui;
import com.hellofresh.challenge.GaugeLog;
import com.hellofresh.challenge.ui.data.User;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;


import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class Steps {
    WebDriver driver = Hooks.getDriver();
    DataStore scenarioStore = DataStoreFactory.getSpecDataStore();

	User user;

    @Step({"I navigate to home page"})
    public void navigateTo() {
    	String url = System.getenv("appUrl");
    	GaugeLog.Add(url);
    	driver.get(url);
    }
    
    @Step({"I click the element with <type>=<selectorValue>"})
    public void sendRandomParameter(String type, String selectorValue){
        driver.findElement(findBy(SelectorType.valueOf(type.toUpperCase()), selectorValue)).click();
    }
    
    @Step({"I send the <parameter> with <value> to element with <type>=<selectorValue>"})
    public void sendParameter(String parameter,String value, String type, String selectorValue){
        driver.findElement(findBy(SelectorType.valueOf(type.toUpperCase()), selectorValue)).sendKeys("");
    }
    
    @Step({"I send the random <parameter> to element with <type>=<selectorValue>"})
    public void sendRandomParameter(String parameter, String type, String selectorValue){
    	String randomValue = scenarioStore.get(parameter).toString();
    	
    	GaugeLog.Add(String.format(" %s : %s ", parameter, randomValue));
    	
    	if (parameter.contains("Birth")) {
    		Select select = new Select(driver.findElement(findBy(SelectorType.valueOf(type.toUpperCase()), selectorValue)));
            select.selectByValue(randomValue);
		}else {
	    	driver.findElement(findBy(SelectorType.valueOf(type.toUpperCase()), selectorValue)).sendKeys(randomValue);
		}
    }
    @Step({"Generate customer data"})
    public void generateCustomerData() {
    	user = new User();
    	String[] customer = {
    			"firstName",
    			"lastName",
    			"email",
    			"dayOfBirth",
    			"monthOfBirth",
    			"yearOfBirth",
    			"password",
    			"zipCode",
    			"city",
    			"address",
    			"phoneNumber",
    			"company",
    			"state"};
    	for (String field : customer) {
    		scenarioStore.put(field, getRandomValue(field));
		}    	
    }
    @Step({"I validate the userName is displayed in the header with <type>=<selectorValue>"})
    public void validateUsername(String type, String selectorValue){
    	String userNameDisplayed = driver.findElement(findBy(SelectorType.valueOf(type.toUpperCase()), selectorValue)).getText();
    	String userNameExpected = scenarioStore.get("firstName") + " " + scenarioStore.get("lastName");
    	Assert.assertEquals(userNameExpected, userNameDisplayed);
    }
    
    @Step({"I validate the element is displayed with <type>=<selectorValue>"})
    public void validateElement(String type, String selectorValue){
    	Boolean elementDisplayed = driver.findElement(findBy(SelectorType.valueOf(type.toUpperCase()), selectorValue)).isDisplayed();
    	Assert.assertTrue(elementDisplayed);
    }
    
    @Step({"I validate the <text> is displayed with <type>=<selectorValue>"})
    public void validateTextExists(String text, String type, String selectorValue){
    	String textDisplayed = driver.findElement(findBy(SelectorType.valueOf(type.toUpperCase()), selectorValue)).getText();
    	Assert.assertEquals(text, textDisplayed);
    }

    @Step({"I validate the <page> is opened"})
    public void validateUrlPage(String page){
    	Boolean pageOpened = driver.getCurrentUrl().contains(page);
    	Assert.assertTrue(pageOpened);
    }
    public String getRandomValue(String parameter) {
    	LocalDate localDate = user.birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	switch (parameter) {
		case "firstName":
			return user.firstName;
		case "lastName":
			return user.lastName;
		case "password":
			return user.password;
		case "email":
			return user.email;
		case "company":
			return user.company;
		case "address":
			return user.address;
		case "city":
			return user.city;
		case "state":
			return user.state;
		case "zipCode":
			return user.zipCode;
		case "phoneNumber":
			return user.phoneNumber;
		case "dayOfBirth":
			return String.valueOf(localDate.getDayOfMonth());	
		case "monthOfBirth":
			return String.valueOf(localDate.getMonthValue());	
		case "yearOfBirth":
			return String.valueOf(localDate.getYear());	
		default:
			break;
		}
		return parameter;
    }
    public enum SelectorType {
        ID, NAME, XPATH, CLASS, TAG, LINK, CSS
    }
    public By findBy(SelectorType selector, String selectorValue){
        switch (selector){
            case ID:
                return By.id(selectorValue);
            case NAME:
                return By.name(selectorValue);
            case XPATH:
                return By.xpath(selectorValue);
            case CLASS:
                return By.className(selectorValue);
            case TAG:
                return By.tagName(selectorValue);
            case LINK:
                return By.linkText(selectorValue);
            case CSS:
                return By.cssSelector(selectorValue);
            default:
                return By.xpath(selectorValue);
        }
    }
}