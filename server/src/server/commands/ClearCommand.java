package server.commands;

import common.data.StudyGroup;
import common.exceptions.DatabaseHandlingException;
import common.exceptions.PermissionDeniedException;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.User;
import server.utility.CollectionManager;
import server.utility.DatabaseCollectionManager;
import server.utility.ResponseOutputer;

public class ClearCommand extends AbstractCommand{

    private CollectionManager collectionManager;

    private DatabaseCollectionManager databaseCollectionManager;

    public ClearCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("clear", "", "очистить коллекцию");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */

    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        int count = 0;
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            for (StudyGroup group : collectionManager.getCollection()) {
                if (group.getOwner().equals(user)) count++;
//                if (count == 0) throw new PermissionDeniedException();
//                if (!databaseCollectionManager.checkGroupUserId(group.getId(), user)) throw new ManualDatabaseEditException();
            }

            if (count == 0) throw new PermissionDeniedException();

            databaseCollectionManager.clearCollection(user);
            collectionManager.clearCollection();
            collectionManager.loadCollection();

            ResponseOutputer.appendln("ClearCollection");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using");
        } catch (DatabaseHandlingException exception) {
//            Outputer.printerror("Phia server: " + exception.getMessage());
            ResponseOutputer.appenderror("DatabaseHandlingException");
        } catch (PermissionDeniedException exception) {
//            Outputer.printerror("Phia server: " + exception.getMessage());
            ResponseOutputer.appenderror("NoughRightsException");
//            ResponseOutputer.appendln("Принадлежащие другим пользователям объекты доступны только для чтения.");
        }
//        } catch (ManualDatabaseEditException exception) {
//            Outputer.printerror("Phia server: " + exception.getMessage());
//            ResponseOutputer.appenderror("Произошло прямое изменение базы данных!");
//            ResponseOutputer.appendln("Перезапустите клиент для избежания возможных ошибок.");
//        }
        return false;
    }

}

