package com.example.spaceapps.ui.tests

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.spaceapps.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI tests for the Login screen.
 * Tests navigation from Login to Home (Rocket List) with valid credentials.
 */
@RunWith(AndroidJUnit4::class)
class LoginUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Test that verifies valid login with admin@lasalle.es / admin1234
     * navigates to the rocket list screen.
     */
    @Test
    fun validLogin_navigatesToRocketList() {
        // Wait for splash screen to finish and login screen to appear
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithText("Log In")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        // Enter valid email
        composeTestRule
            .onNodeWithText("Email")
            .performTextInput("admin@lasalle.es")

        // Enter valid password
        composeTestRule
            .onNodeWithText("Password")
            .performTextInput("admin1234")

        // Click login button (use Role.Button to distinguish from the title)
        val isButton = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button)
        composeTestRule
            .onNode(hasText("Log In") and isButton)
            .performClick()

        // Verify navigation to Home screen by checking for "Explore Rockets" text
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithText("Explore Rockets")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        // Assert that we are on the Home screen
        composeTestRule
            .onNodeWithText("Explore Rockets")
            .assertIsDisplayed()
    }
}

