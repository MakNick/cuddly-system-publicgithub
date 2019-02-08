Feature: Login as an organizer
    I will attempt to login as an organizor

    Scenario: I succesfully logged in
        Given I am on the login page
        When I fill in admin as my username
        And I fill in admin123 as my password
        And I press the login button
        Then I should see the conference tab in the navbar

    Scenario: I fill in the wrong password
        Given I am on the login page
        When I fill in admin my username
        And I fill in admin12 as my password
        And I press the login button
        Then I should see the "Login failed: Error -> Unauthorized" message
    
    Scenario: I forget to fill in my password
        Given I am on the login page
        When I fill in admin as my username
        And I press the login button
        Then I should see the "Password is required" message

    Scenario: I forgot to enter my username
        Given I am on the login page
        When I fill in admin123 as my password
        And I press the login button
        Then I should see the "Username is required" message

    Scenario: My password is not long enough
        Given I am on the login page
        When I fill in admin as my username
        And I fill in 123 as my password
        Then I should see the "Password must be at least 6 characters" message