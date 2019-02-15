Feature: I want to register a new account
    I want to make an account to login so that i can view my submitted presentation drafts

    Scenario: I fill in everything correctly
        Given I am on the register page
        When I fill in piet as my name
        And I fill in piet001 as my username
        And I fill in "piet@msn.com" as my email
        And I fill in piet123 as my password
        And I press the register button
        Then I should see the "Your registration is successful. Please login!" message

    Scenario: I fill in everything correctly but the user already exists
        Given I am on the register page
        When I fill in piet as my name
        And I fill in piet001 as my username
        And I fill in "piet@msn.com" as my email
        And I fill in piet123 as my password
        And I press the register button
        Then I should see the "Signup failed! Fail -> Username is already taken!" message

    Scenario: I forgot to fill in my name
        Given I am on the register page
        When I fill in piet001 as my username
        And I fill in "piet@msn.com" as my email
        And I fill in piet123 as my password
        Then I should see the "Name is required" message

    Scenario: I forgot to fill in my username
        Given I am on the register page
        When I fill in piet as my name
        And I fill in "piet@msn.com" as my email
        And I fill in piet123 as my password
        Then I should see the "Username is required" message

    Scenario: I forgot to fill in my email
        Given I am on the register page
        When I fill in piet as my name
        And I fill in piet001 as my username
        And I fill in piet123 as my password
        Then I should see the "Email is required" message

    Scenario: I fill in an incorrect email address
        Given I am on the register page
        When I fill in piet as my name
        And I fill in piet001 as my username
        And I fill in "piet" as my email
        And I fill in piet123 as my password
        Then I should see the "Email must be a valid email address" message

    Scenario: I forgot to fill in my password
        Given I am on the register page
        When I fill in piet as my name
        And I fill in piet001 as my username
        And I fill in "piet@msn.com" as my email
        Then I should see the "Password is required" message

    Scenario: My password does not match the required length
        Given I am on the register page
        When I fill in piet as my name
        And I fill in piet001 as my username
        And I fill in "piet@msn.com" as my email
        And I fill in 123 as my password
        Then I should see the "Password must be at least 6 characters" message

    