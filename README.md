**Expense Tracking: NEED GUI**
Implement a robust expense tracking system that allows users to add, edit, and delete their
expenses. Each expense should include details such as the amount, date, and category. Provide at
least five predefined expense categories (e.g., food, transportation, entertainment, utilities, and
miscellaneous). Users should be able to view their expenses and filter them based on date ranges
or categories.

**Budget Management: NEED GUI**
Create a budget management feature that enables users to set monthly budgets for different
expense categories. The application should provide a visual representation of budget progress,
such as a progress bar, for each category. Implement an alert system that notifies users when they
have spent 80% or more of their allocated budget in any category. This feature should help users
stay on track with their financial goals.

**Financial Reports NEED GUI:**
Develop a reporting system that generates monthly spending summaries. The report should
include total expenses, a breakdown by category, and a comparison with the set budgets.
Additionally, create at least one graphical visualization of spending patterns, such as a pie chart
showing the proportion of expenses in each category. These reports should provide users with
insights into their spending habits.

**User Interface: DONE**
Design and implement a user-friendly interface using Swing components. The GUI should
include a dashboard displaying the user's current financial status, forms for adding and editing
expenses, a budget setting and monitoring interface, and a dedicated view for financial reports
and visualizations. Ensure that the interface is intuitive and easy to navigate.

**Data Persistence: DONE**
Implement a data persistence mechanism using file I/O operations. The application should be
able to save user data, including account information, expenses, and budgets, to a file. Similarly,
it should be able to load this data when the user logs in. Ensure that the data is stored in a
structured format that can be easily read and written.

**Technical Requirements: DONE**
Utilize object-oriented programming principles throughout your implementation. Design
appropriate class hierarchies, demonstrating the use of inheritance, polymorphism, and
encapsulation. Implement robust exception handling to manage invalid inputs, file operation
errors, and other potential issues.

**Data Import and Export:**
Implement a feature that allows users to import transactions from a text file. The application
should be able to read and process transaction data stored in a specific format. The expected
format for each transaction in the text file should be:
Date,Category,Amount,Description
For example:
2024-01-01,Groceries,50.00,Supermarket
2024-01-03,Transportation,15.00,Bus Ticket
2024-01-05,Entertainment,30.00,Movie Night
Create a function that reads this file and populates the user's expense tracker with the imported
data. The function should handle potential errors gracefully, such as incorrect file formats or
missing data.
Additionally, implement an export feature that allows users to save their current transactions to a
text file in the same format. This will enable users to backup their data or transfer it to other
systems.

**File Handling Requirements:**
Implement robust error handling for file operations, including file not found errors and format
inconsistencies.
Provide clear feedback to the user about the success or failure of import/export operations.
Validate the imported data to ensure it meets the required format and contains valid information.