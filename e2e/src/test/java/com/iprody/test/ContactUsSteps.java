package com.iprody.test;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ContactUsSteps {

    @Given("I access the webdriver university contact us page")
    public void iAccessTheWebdriverUniversityContactUsPage() {
        System.out.println("Test1");
    }

    @When("I enter a unique first name")
    public void iEnterAUniqueFirstName() {
        System.out.println("Test2");
    }

    @And("I enter a unique last name")
    public void iEnterAUniqueLastName() {
        System.out.println("Test3");
    }

    @And("I enter a unique email address")
    public void iEnterAUniqueEmailAddress() {
        System.out.println("Test4");
    }

    @And("I enter a unique comment")
    public void iEnterAUniqueComment() {
        System.out.println("Test5");
    }

    @And("I click on submit button")
    public void iClickOnSubmit_button() {
        System.out.println("Test6");
    }

    @Then("I should be presented with a successful contact us submission message")
    public void iShouldBePresentedWithASuccessfulContactUsSubmissionMessage() {
        System.out.println("Test7");
    }

}
