package server.utility;

import server.commands.Command;
import common.exceptions.HistoryIsEmptyException;
import common.interaction.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CommandManager {

    private final int COMMAND_HISTORY_SIZE = 11;

    private String[] commandHistory = new String[COMMAND_HISTORY_SIZE];

    private List<Command> commands = new ArrayList<>();

    private Command helpCommand;

    private Command infoCommand;

    private Command showCommand;

    private Command addCommand;

    private Command updateCommand;

    private Command removeByIdCommand;

    private Command clearCommand;

    private Command exitCommand;

    private Command executeScriptCommand;

    private Command historyCommand;

    private Command sumOfTransferredStudentsCommand;

    private Command sortCommand;

    private Command removeAtIndexCommand;

    private Command minBySemesterEnumCommand;

    private Command groupCountingByCoordinatesCommand;

    private Command serverExitCommand;

    private Command loginCommand;

    private Command registerCommand;

    private Command refreshCommand;

    private ReadWriteLock historyLocker = new ReentrantReadWriteLock();

    private ReadWriteLock collectionLocker = new ReentrantReadWriteLock();


    /**
     * ReadWriteLock là một giao diện trong Java thuộc gói java.util.concurrent.locks,
     * cung cấp một cơ chế đồng bộ hóa để quản lý truy cập vào dữ liệu có thể đọc và ghi.
     * Giao diện này định nghĩa hai khóa (lock) riêng biệt là khóa đọc (read lock) và khóa ghi (write lock),
     * cho phép nhiều luồng đọc truy cập vào dữ liệu cùng một lúc, nhưng chỉ cho phép một luồng viết truy cập
     * vào dữ liệu tại một thời điểm.
     *
     * ReadWriteLock hữu ích trong các tình huống mà dữ liệu có thể được đọc nhiều lần nhưng chỉ được ghi
     * bởi một luồng duy nhất. Nó cung cấp cơ chế tối ưu cho việc truy cập đọc song song, đồng thời đảm bảo
     * tính nhất quán và an toàn của dữ liệu khi có luồng đang thực hiện ghi.
     */

    public CommandManager(Command helpCommand, Command infoCommand, Command showCommand, Command addCommand, Command updateCommand,
                          Command removeByIdCommand, Command clearCommand, Command exitCommand, Command executeScriptCommand,
                          Command removeAtIndexCommand, Command sortCommand, Command historyCommand, Command sumOfTransferredStudentsCommand,
                          Command minBySemesterEnumCommand, Command groupCountingByCoordinatesCommand, Command serverExitCommand, Command loginCommand,
                          Command registerCommand,Command refreshCommand) {
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.addCommand = addCommand;
        this.updateCommand = updateCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.clearCommand = clearCommand;
        this.exitCommand = exitCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.removeAtIndexCommand = removeAtIndexCommand;
        this.sortCommand = sortCommand;
        this.historyCommand = historyCommand;
        this.sumOfTransferredStudentsCommand = sumOfTransferredStudentsCommand;
        this.minBySemesterEnumCommand = minBySemesterEnumCommand;
        this.groupCountingByCoordinatesCommand = groupCountingByCoordinatesCommand;
        this.serverExitCommand = serverExitCommand;
        this.loginCommand = loginCommand;
        this.registerCommand = registerCommand;
        this.refreshCommand = refreshCommand;

        commands.add(helpCommand);
        commands.add(infoCommand);
        commands.add(showCommand);
        commands.add(addCommand);
        commands.add(updateCommand);
        commands.add(removeByIdCommand);
        commands.add(clearCommand);
        commands.add(exitCommand);
        commands.add(executeScriptCommand);
        commands.add(removeAtIndexCommand);
        commands.add(sortCommand);
        commands.add(historyCommand);
        commands.add(sumOfTransferredStudentsCommand);
        commands.add(minBySemesterEnumCommand);
        commands.add(groupCountingByCoordinatesCommand);
        commands.add(serverExitCommand);
    }

    /**
     * Adds command to command history.
     *
     * @param commandToStore Command to add.
     * @param user           User object.
     */

    public void addToHistory(String commandToStore, User user) {
        historyLocker.writeLock().lock();
        try {
            for (Command command : commands) {
                if (command.getName().equals(commandToStore)) {
                    for (int i = COMMAND_HISTORY_SIZE - 1; i > 0; i--) {
                        commandHistory[i] = commandHistory[i - 1];
                    }
                    commandHistory[0] = commandToStore + " (" + user.getUsername() + ')';
                }
            }
        } finally {
            historyLocker.writeLock().unlock();
        }
    }

    /**
     * Prints info about the all commands.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean help(String stringArgument, Object objectArgument, User user) {
        if (helpCommand.execute(stringArgument, objectArgument, user)) {
            for (Command command : commands) {
                ResponseOutputer.appendtable(command.getName() + " " + command.getUsage(), command.getDescription());
            }
            return true;
        } else return false;
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean info(String stringArgument, Object objectArgument, User user) {
        collectionLocker.readLock().lock();
        try {
            return infoCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.readLock().unlock();
        }
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean show(String stringArgument, Object objectArgument, User user) {
        collectionLocker.readLock().lock();
        try {
            return showCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.readLock().unlock();
        }
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean add(String stringArgument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return addCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean update(String stringArgument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return updateCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean removeById(String stringArgument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return removeByIdCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean clear(String stringArgument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return clearCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean exit(String stringArgument, Object objectArgument, User user) {
        return exitCommand.execute(stringArgument, objectArgument, user);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean executeScript(String stringArgument, Object objectArgument, User user) {
        return executeScriptCommand.execute(stringArgument, objectArgument, user);
    }

    public boolean sumOfTransferredStudents(String stringArgument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return sumOfTransferredStudentsCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    public boolean sort(String stringArgument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return sortCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    public boolean removeAtIndex(String stringArgument, Object objectArgument, User user){
        collectionLocker.writeLock().lock();
        try {
            return removeAtIndexCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    public boolean minBySemesterEnum(String stringArgument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return minBySemesterEnumCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    public boolean groupCountingByCoordinates(String stringArgument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return groupCountingByCoordinatesCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    /**
     * Prints the history of used commands.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean history(String stringArgument, Object objectArgument, User user) {
        if (historyCommand.execute(stringArgument, objectArgument, user)) {
            historyLocker.readLock().lock();
            try {
                if (commandHistory.length == 0) throw new HistoryIsEmptyException();
                ResponseOutputer.appendln("LastUsingCommand");
                String arg = "\n";
                for (String command : commandHistory) {
                    if (command != null) ResponseOutputer.appendln(" " + command);
                }
                return true;
            } catch (HistoryIsEmptyException exception) {
                ResponseOutputer.appendln("LastUsingCommandException");
            } finally {
                historyLocker.readLock().unlock();
            }
        }
        return false;
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean serverExit(String stringArgument, Object objectArgument, User user) {
        return serverExitCommand.execute(stringArgument, objectArgument, user);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean login(String stringArgument, Object objectArgument, User user) {
        return loginCommand.execute(stringArgument, objectArgument, user);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean register(String stringArgument, Object objectArgument, User user) {
        return registerCommand.execute(stringArgument, objectArgument, user);
    }

    public boolean refresh(String stringArgument, Object objectArgument, User user) {
        return refreshCommand.execute(stringArgument, objectArgument, user);
    }

}

