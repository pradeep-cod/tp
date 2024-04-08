package activeedge.ui;

import static activeedge.task.TaskList.tasksList;

import activeedge.task.LogExercise;
import activeedge.task.Task;
import activeedge.task.WaterTask;
import activeedge.task.MealTask;

import java.time.format.DateTimeFormatter;


public class CommandUi {

    static final String LINE = "____________________________________________________________\n";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");

    public static void printWelcomeMessage() {
        String logo = "ACTIVE EDGE!";
        System.out.println("Welcome to " + logo);
        System.out.println("Take the next step in your Healthy Lifestyle!");
    }

    public static void printFullList() {
        System.out.println("Logged data for today:");

        System.out.println("Food ");
        int j = 1;
        for (int i = 0; i < tasksList.size(); i++) {
            if (tasksList.get(i).toString().startsWith("Meal")) {
                System.out.print(j + ". " + tasksList.get(i).toString().substring(5));
                System.out.println("");
                j++;
            }
        }
        System.out.println("Water:");
        int k = 1;
        for (int i = 0; i < tasksList.size(); i++) {
            if (tasksList.get(i).toString().startsWith("Water")) {
                System.out.print(k + ". " + tasksList.get(i).toString().substring(6));
                System.out.println("");
                k++;
            }
        }
        System.out.println("Exercises:");
        int l = 1;
        for (int i = 0; i < tasksList.size(); i++) {
            if (tasksList.get(i).toString().startsWith("Exercise")) {
                System.out.print(l + ". " + tasksList.get(i).toString().substring(9));
                System.out.println("");
                l++;
            }
        }

    }

    public static void printMealLogMessage(MealTask mealTask) {
        System.out.println("You've logged " + Integer.toString(mealTask.getServings()) +
                " servings" + " of " + mealTask.getFoodName() + ".") ;
        System.out.println("Estimated calories: " + Integer.toString(mealTask.getMealCalories()) + " kcal");
    }

    public static void printExerciseLogMessage(LogExercise logExercise) {
        System.out.println("You've logged " + Integer.toString(logExercise.getDuration()) +
                " minutes" + " of " + logExercise.getExerciseName() + ".") ;
        System.out.println("Estimated calories burnt: " + Integer.toString(logExercise.getCaloriesBurnt()) + " kcal");
    }

    public static void printShowCalMessage() {
        int totalCalories = 0;
        int totalCaloriesFromMeals = 0;
        int totalCaloriesFromExercises = 0;
        String goal = "0";
        for (int i = 0; i < tasksList.size(); i++) {
            String[] parts = tasksList.get(i).toString().split(" ");
            int len = parts.length;
            String taskString = tasksList.get(i).toString();
            int kcalIndex = -1;
            for (int j = 0; j < len; j++) {
                if (parts[j].equals("kcal")) {
                    kcalIndex = j - 1; // Assuming calorie value is just before "kcal"
                    break;
                }
            }

            // Check if kcal index is found and the part at that index is a valid integer
            if (kcalIndex >= 0 && kcalIndex < parts.length) {
                String calorieString = parts[kcalIndex];
                if (calorieString.matches("\\d+")) { // Check if it's a valid integer
                    int calories = Integer.parseInt(calorieString);
                    if (taskString.startsWith("Meal")) {
                        totalCaloriesFromMeals += calories;
                    } else if (taskString.startsWith("Exercise")) {
                        totalCaloriesFromExercises += calories;
                    }
                } else {
                    System.out.println("Skipping non-integer calorie value: " + calorieString);
                }
            }
            if(tasksList.get(i).toString().startsWith("Goal")) {
                if (parts[1].equals("c")) {
                    goal = parts[2].toString();
                }
            }
        }
        totalCalories = totalCaloriesFromMeals - totalCaloriesFromExercises;
        int totalSurplus = totalCalories - Integer.parseInt(goal);
        System.out.print("Total calories consumed today: ");
        System.out.println("You have burned " + totalCaloriesFromExercises + " today!");
        System.out.println("You have consumed " + totalCaloriesFromMeals + " kcal out of " + goal + " kcal");
        if(totalCaloriesFromMeals > Integer.parseInt(goal)) {
            System.out.println("You have exceeded your calorie intake goal!");
        } else{
            System.out.println("You're doing an excellent job of managing your calorie intake!");
        }
        if(totalSurplus > 0){
            System.out.println("Calorie surplus at the moment --> " + totalSurplus);
        }
        else{
            System.out.println("Calorie deficit at the moment --> " + -totalSurplus);
        }
    }

    public static void printWaterLogMessage(WaterTask newWaterTask) {
        System.out.println("Successfully logged " + newWaterTask.getQuantity() + " ml of water.");
    }

    public static void printWaterIntakeMessage(int totalWaterIntake, int waterGoal) {
        double percentage = ((double) totalWaterIntake / waterGoal) * 100;
        System.out.println("Total water consumed today: " + totalWaterIntake +
                " ml (" + String.format("%.0f%%", percentage) + " of " + waterGoal + "ml goal).");
    }


    public static void printMatchingTasks(String word) {
        System.out.println(LINE + " Here are the matching tasks in your list:");
        int matchingTasksIndex = 1;
        boolean found = false;

        for (Task task : tasksList) {
            String taskString = task.toString().trim(); // Trim the task string
            if (taskString.startsWith("Meal") && taskString.contains(word)) {
                System.out.print(matchingTasksIndex + ". ");
                System.out.println(taskString.substring(5) + " kcal");
                matchingTasksIndex++;
                found = true;

            } else if (taskString.startsWith("Water") && taskString.contains(word)) {
                System.out.print(matchingTasksIndex + ". ");
                System.out.println(taskString.substring(6) + " ml");
                matchingTasksIndex++;
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching tasks found.");
        }

        System.out.println(LINE);
    }

    /**
     * Prints a message indicating that the delete request format is invalid.
     */
    public static void printInvalidDeleteFormatMessage() {
        System.out.println("This is an invalid request. Please try again!");
    }

    /**
     * Prints a message confirming the deletion of a task.
     * @param deletedTask The task that was deleted.
     */
    public static void printTaskDeletedMessage(Task deletedTask) {
        System.out.println("Log deleted: " + deletedTask.getDescription());
    }

    public static void printFoodItemNotFoundMessage(String description){
        System.out.println(description + " is not found in our food database.\n" +
                "Please enter the following command to add it to the database and log your meal.\n\n" +
                "add m/[FOOD] c/[CALORIES_PER_SERVING(kCal)] s/[NUMBER_OF_SERVINGS]\n\n" +
                "Eg: 'add m/"+ description +" c/120 s/2'\n"
        );
    }

    public static void printExerciseItemNotFoundMessage(String exerciseName){
        System.out.println(exerciseName + " is not found in our exercise database.\n" +
                "Please enter the following command to add it to the database and log your exercise.\n\n" +
                "add e/[EXERCISE] c/[CALORIES_BURNT_PER_MIN(kCal)] s/[DURATION_IN_MIN]\n\n" +
                "Eg: 'add e/"+ exerciseName +" c/120 d/2'\n"
        );
    }


    public static void printAddFoodItemMessage(String description){
        System.out.println(description + " has been added to the food database.\n" +
                "logging your meal.......\n"
        );
    }

    public static void printAddExerciseMessage(String exerciseName) {
        System.out.println(exerciseName + " has been added to the exercise database.\n" +
                "logging your exercise.......\n"
        );
    }


    public static void printTaskNotFoundMessage() {
        System.out.println("Task not found.");
    }
    public static void printShowSummaryMessage(int totalCalories,int totalWaterIntake, int totalCaloriesBurnt,
                                              String calorieGoal, String waterGoal,
                                               int netCalories, String calorieStatus) {
        System.out.println("Daily Summary:");
        System.out.println("Total calories consumed: " + totalCalories + " kcal");
        System.out.println("Total water consumed: " + totalWaterIntake + " ml");
        System.out.println("Total calories burnt: " + totalCaloriesBurnt + " kcal");

        System.out.println("Calorie goal: " + calorieGoal + " kcal");
        System.out.println("Water goal: " + waterGoal + " ml");

        System.out.println("Net calories: " + netCalories + " kcal");
        System.out.println("Calorie status: " + calorieStatus);
    }

    public static void printAllTasksClearedMessage() {
        System.out.println("All logged data has been cleared.");
    }
    public static void printDataAlreadyClearedMessage() {
        System.out.println("Logged data has already been cleared.");
    }

    /**
     * Prompts the user to log an exercise instead of adding it because it already exists.
     * @param exerciseName The name of the existing exercise.
     */

    public static void promptLogExerciseMessage(String exerciseName) {
        System.out.println("The exercise '" + exerciseName + " already exists. Please log " + exerciseName +
                " instead of adding it.");
    }

    /**
     * Prompts the user to log a food item instead of adding it because it already exists.
     * @param foodItemName The name of the existing food item.
     */
    public static void promptLogFoodMessage(String foodItemName) {
        System.out.println("The food item '" + foodItemName + "' already exists. Please log " + foodItemName +
                " instead of adding it.");
    }

    public static void printCalorieExceedingWarning() {
        System.out.println("WARNING: You are exceeding your calorie intake!");
    }


    public static void printErrorMessage(String errorMessage) {
        System.out.println("ERROR: " + errorMessage);
    }
}
