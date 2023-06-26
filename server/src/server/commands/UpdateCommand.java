package server.commands;

//import data.*;
//import exceptions.*;
import common.data.*;
import common.exceptions.*;
import common.interaction.GroupRaw;
import common.interaction.User;
import server.utility.CollectionManager;
import server.utility.DatabaseCollectionManager;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;

public class UpdateCommand extends AbstractCommand{

    private CollectionManager collectionManager;

    private DatabaseCollectionManager databaseCollectionManager;

    public UpdateCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("update", "<ID> {element}", "обновить значение элемента коллекции по ID");
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
        try {
            if (stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            long id = Long.parseLong(stringArgument);
            if (id <= 0) throw new NumberFormatException();
            StudyGroup oldGroup = collectionManager.getById(id);
            if (oldGroup == null) throw new GroupNotFoundException();
            if (!oldGroup.getOwner().equals(user)) throw new PermissionDeniedException();
            if (!databaseCollectionManager.checkGroupUserId(oldGroup.getId(), user)) throw new ManualDatabaseEditException();
            GroupRaw groupRaw = (GroupRaw) objectArgument;

            databaseCollectionManager.updateGroupById(id, groupRaw);

            String name = groupRaw.getName() == null ? oldGroup.getName() : groupRaw.getName();
            Coordinates coordinates = groupRaw.getCoordinates() == null ? oldGroup.getCoordinates() : groupRaw.getCoordinates();
            LocalDateTime creationDate = oldGroup.getCreationDate();
            Long studentsCount = groupRaw.getStudentsCount() < 0 ? oldGroup.getStudentsCount() : groupRaw.getStudentsCount();
            int transferredStudents = groupRaw.getTransferredStudents() < 0 ? oldGroup.getTransferredStudents() : groupRaw.getTransferredStudents();
            FormOfEducation formOfEducation = groupRaw.getFormOfEducation() == null ? oldGroup.getFormOfEducation() : groupRaw.getFormOfEducation();
            Semester semester = groupRaw.getSemester() == null ? oldGroup.getSemesterEnum() : groupRaw.getSemester();
            Person groupAdmin = groupRaw.getGroupAdmin() == null ? oldGroup.getGroupAdmin() : groupRaw.getGroupAdmin();

            collectionManager.removeFromCollection(oldGroup);
            collectionManager.addToCollection(new StudyGroup(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    studentsCount,
                    transferredStudents,
                    formOfEducation,
                    semester,
                    groupAdmin,
                    user
            ));
            collectionManager.loadCollection();
            ResponseOutputer.appendln("GroupChange");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("CollectionIsEmptyException");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("IdMustBeNumberException");
        } catch (GroupNotFoundException exception) {
            ResponseOutputer.appenderror("IdOfGroupException");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("ClientObjectException");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("DatabaseHandlingException");
        } catch (PermissionDeniedException exception) {
            ResponseOutputer.appenderror("NoughRightsException");
        } catch (ManualDatabaseEditException exception) {
            ResponseOutputer.appenderror("ManualDatabaseException");
        }
        return false;
    }
}

