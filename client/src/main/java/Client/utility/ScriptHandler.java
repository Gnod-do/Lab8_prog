package Client.utility;

import Client.App;
import common.data.Coordinates;
import common.data.FormOfEducation;
import common.data.Person;
import common.data.Semester;
import common.exceptions.CommandUsageException;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.ScriptRecursionException;
import common.interaction.GroupRaw;
import common.interaction.Request;
import common.interaction.ResponseCode;
import common.interaction.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class ScriptHandler {
    private final int maxRewriteAttempts = 1;

    private Scanner userScanner;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();
    public ScriptHandler(File scriptFile) {
        try {
            userScanner = new Scanner(scriptFile);
            scannerStack.add(userScanner);
            scriptStack.add(scriptFile);
        } catch (Exception exception) { /* ? */ }
    }

    /**
     * Receives user input.
     *
     * @param serverResponseCode Previous response code.
     * @param user               User object.
     * @return New request to server.
     */
    public Request handle(ResponseCode serverResponseCode, User user) {
        String userInput;
        String[] userCommand;
        ProcessingCode processingCode;
        int rewriteAttempts = 0;
        try {
            do {
                try {
                    if (serverResponseCode == ResponseCode.ERROR || serverResponseCode == ResponseCode.SERVER_EXIT)
                        throw new IncorrectInputInScriptException();
                    while (!scannerStack.isEmpty() && !userScanner.hasNextLine()) {
                        userScanner.close();
                        userScanner = scannerStack.pop();
                        if (!scannerStack.isEmpty()) scriptStack.pop();
                        else return null;
                    }
                    userInput = userScanner.nextLine();
                    if (!userInput.isEmpty()) {
                        Outputer.print(App.PS1);
                        Outputer.println(userInput);
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    Outputer.println();
                    Outputer.printerror("CommandErrorException");
                    userCommand = new String[]{"", ""};
                    rewriteAttempts++;
                    if (rewriteAttempts >= maxRewriteAttempts) {
                        Outputer.printerror("RewriteAttemptsException");
                        System.exit(0);
                    }
                }
                processingCode = processCommand(userCommand[0], userCommand[1]);
            } while (userCommand[0].isEmpty());
            try {
                if (serverResponseCode == ResponseCode.ERROR || processingCode == ProcessingCode.ERROR)
                    throw new IncorrectInputInScriptException();
                switch (processingCode) {
                    case OBJECT:
                        GroupRaw groupAddRaw = generateGroupAdd();
                        return new Request(userCommand[0], userCommand[1], groupAddRaw, user);
                    case UPDATE_OBJECT:
                        GroupRaw marineUpdateRaw = generateGroupUpdate();
                        return new Request(userCommand[0], userCommand[1], marineUpdateRaw, user);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                            throw new ScriptRecursionException();
                        scannerStack.push(userScanner);
                        scriptStack.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        Outputer.println("ScriptRunning", scriptFile.getName());
                        break;
                }
            } catch (FileNotFoundException exception) {
                Outputer.printerror("ScriptFileNotFoundException");
                throw new IncorrectInputInScriptException();
            } catch (ScriptRecursionException exception) {
                Outputer.printerror("ScriptRecursionException");
                throw new IncorrectInputInScriptException();
            }
        } catch (IncorrectInputInScriptException exception) {
            OutputerUI.error("IncorrectInputInScriptException");
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
            scriptStack.clear();
            return null;
        }
        return new Request(userCommand[0], userCommand[1], null, user);
    }

    /**
     * Processes the entered command.
     *
     * @return Status of code.
     */
    private ProcessingCode processCommand(String command, String commandArgument) {
        try {
            switch (command) {
                case "":
                    return ProcessingCode.ERROR;
                case "info":
                    if (!commandArgument.isEmpty())throw new CommandUsageException();
                    break;
                case "add":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "update":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID> {element}");
                    return ProcessingCode.UPDATE_OBJECT;
                case "remove_by_id":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID>");
                    break;
                case "clear":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "save":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<file_name>");
                    return ProcessingCode.SCRIPT;
                case "history":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "server_exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "group_counting_by_coordinates":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "min_by_semester_enum":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "remove_at_index":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<index> {element}");
                    break;
                case "sort":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "sum_of_transferred_students":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
//                case "refresh":
//                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
//                    break;
                default:
                    Outputer.println("CommandNotFoundException", command);
                    return ProcessingCode.ERROR;
            }
        } catch (CommandUsageException exception) {
            if (exception.getMessage() != null) command += " " + exception.getMessage();
            Outputer.println("Using: '" + command + "'");
            return ProcessingCode.ERROR;
        }
        return ProcessingCode.OK;
    }

    /**
     * Generates group to add.
     *
     * @return Group to add.
     * @throws IncorrectInputInScriptException When something went wrong in script.
     */

    private GroupRaw generateGroupAdd() throws IncorrectInputInScriptException{
        GroupAsker groupAsker = new GroupAsker(userScanner);
        return new GroupRaw(
                groupAsker.askName(),
                groupAsker.askCoordinates(),
                groupAsker.askStudentsCount(),
                groupAsker.askTransferredStudents(),
                groupAsker.askFormOfEducation(),
                groupAsker.askSemester(),
                groupAsker.askGroupAdmin()
        );
    }

    /**
     * Generates group to update.
     *
     * @return Group to update.
     * @throws IncorrectInputInScriptException When something went wrong in script.
     */

    private GroupRaw generateGroupUpdate() throws IncorrectInputInScriptException {
        GroupAsker groupAsker = new GroupAsker(userScanner);
        String name = groupAsker.askQuestion("ChangeNameQuestion") ?
                groupAsker.askName() : null;
        Coordinates coordinates = groupAsker.askQuestion("ChangeCoordinatesQuestion") ?
                groupAsker.askCoordinates(): null;
        Long studentsCount = groupAsker.askQuestion("ChangeStudentsCountQuestion") ?
                groupAsker.askStudentsCount(): -1;
        int transferredStudents = groupAsker.askQuestion("ChangeTransferredStudentsQuestion") ?
                groupAsker.askTransferredStudents(): -1;
        FormOfEducation formOfEducation = groupAsker.askQuestion("ChangeFormOfEducationQuestion") ?
                groupAsker.askFormOfEducation(): null;
        Semester semester = groupAsker.askQuestion("ChangeSemesterQuestion") ?
                groupAsker.askSemester(): null;
        Person groupAdmin = groupAsker.askQuestion("ChangeGroupAdminQuestion") ?
                groupAsker.askGroupAdmin(): null;
        return new GroupRaw(
                name,
                coordinates,
                studentsCount,
                transferredStudents,
                formOfEducation,
                semester,
                groupAdmin
        );
    }



}
