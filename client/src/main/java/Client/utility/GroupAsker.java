package Client.utility;

import Client.App;
import common.data.*;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotInDeclaredLimitsException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GroupAsker {

    private Scanner userScanner;

    public GroupAsker(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * Asks a user the group's name.
     *
     * @return Group's name.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */

    public String askName() throws IncorrectInputInScriptException {
        String name;
        while (true) {
            try {
                Outputer.println("EnterName");
                Outputer.print(App.PS2);
                name = userScanner.nextLine().trim();
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("NameNotIdentifiedException");
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("NameEmptyException");
            } catch (IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                System.exit(0);
            }
        }
        return name;
    }

    /**
     * Asks a user the group's X coordinate.
     *
     * @return Group's X coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */

    public Integer askX() throws IncorrectInputInScriptException {
        String strX;
        Integer x;
        while (true) {
            try {
                Outputer.println("EnterX");
                Outputer.print(App.PS2);
                strX = userScanner.nextLine().trim();
                x = Integer.parseInt(strX);
                if (x >= StudyGroup.MAX_X) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("XNotIdentifiedException");
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("X coordinate cannot exceed " + StudyGroup.MAX_X + "!");
            } catch (NumberFormatException exception) {
                Outputer.printerror("XMustBeNumberException");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                System.exit(0);
            }
        }
        return x;

    }

    /**
     * Asks a user the group's Y coordinate.
     *
     * @return Group's Y coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */

    public Long askY() throws IncorrectInputInScriptException {
        String strY;
        Long y;
        while (true) {
            try {
                Outputer.println("EnterY");
                Outputer.print(App.PS2);
                strY = userScanner.nextLine().trim();
                y = Long.parseLong(strY);
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("YNotIdentifiedException");
            } catch (NumberFormatException exception) {
                Outputer.printerror("YMustBeNumberException");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                System.exit(0);
            }
        }
        return y;
    }

    /**
     * Asks a user the group's coordinates.
     *
     * @return group's coordinates.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */

    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        Integer x;
        Long y;
        x = askX();
        y = askY();
        return new Coordinates(x, y);
    }


    /**
     * Asks a user the number of students in this group.
     *
     * @return Group's studentsCount.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */

    public Long askStudentsCount() throws IncorrectInputInScriptException {
        String strStudentsCount;
        Long studentsCount;
        while (true) {
            try {
                Outputer.println("EnterStudentsCount");
                Outputer.print(App.PS2);
                strStudentsCount = userScanner.nextLine().trim();
                studentsCount = Long.parseLong(strStudentsCount);
                if (studentsCount <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("StudentsCountNotIdentifiedException");
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("StudentsCountNotInDeclaredLimitsException");
            } catch (NumberFormatException exception) {
                Outputer.printerror("StudentsCountMustBeNumberException");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                System.exit(0);
            }
        }
        return studentsCount;
    }

    /**
     * Asks a user the transferred students.
     *
     * @return Number students transferred.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */

    public int askTransferredStudents() throws IncorrectInputInScriptException {
        String strTransferredStudents;
        int transferredStudents;
        while (true) {
            try {
                Outputer.println("EnterTransferredStudents");
                Outputer.print(App.PS2);
                strTransferredStudents = userScanner.nextLine().trim();
                transferredStudents = Integer.parseInt(strTransferredStudents);
                if (transferredStudents <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("TransferredStudentsNotIdentifiedException");
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("TransferredStudentsNotInDeclaredLimitsException");
            } catch (NumberFormatException exception) {
                Outputer.printerror("TransferredStudentsMustBeNumber");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                System.exit(0);
            }
        }
        return transferredStudents;
    }

    /**
     * Asks a user the group's form of education.
     *
     * @return Group's form of education.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */

    public FormOfEducation askFormOfEducation() throws IncorrectInputInScriptException {
        String strFormOfEducation;
        FormOfEducation formOfEducation;
        while (true) {
            try {
                Outputer.println("EnterFormOfEducation");
                Outputer.print(App.PS2);
                strFormOfEducation = userScanner.nextLine().trim();
                formOfEducation = FormOfEducation.valueOf(strFormOfEducation.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("FormOfEducationNotIdentifiedException");
            } catch (IllegalArgumentException exception) {
                Outputer.printerror("NoSuchFormOfEducation");
            } catch (IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                System.exit(0);
            }
        }
        return formOfEducation;
    }

    /**
     * Asks a user the group's semester.
     * @return Group's semester.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */

    public Semester askSemester() throws IncorrectInputInScriptException {
        String strSemester;
        Semester semester;
        while (true) {
            try {
                Outputer.println("EnterSemester");
                Outputer.print(App.PS2);
                strSemester = userScanner.nextLine().trim();
                semester = Semester.valueOf(strSemester.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("SemesterNotIdentifiedException");
            } catch (IllegalArgumentException exception) {
                Outputer.printerror("NoSuchSemester");
            } catch (IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                System.exit(0);
            }
        }
        return semester;
    }

    /**
     * Asks a user the name of group admin.
     * @return group admin name.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */

    public String askGroupAdminName() throws IncorrectInputInScriptException {
        String groupAdminName;
        while (true) {
            try {
                Outputer.println("EnterAdministratorName");
                Outputer.print(App.PS2);
                groupAdminName = userScanner.nextLine().trim();
                if (groupAdminName.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("AdministratorNameNotIdentifiedException");
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("NameEmptyException");
            } catch (IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                System.exit(0);
            }
        }
        return groupAdminName;
    }

    /**
     * Asks a user the birthday of admin group.
     * @return birthday of admin group.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */

    public LocalDate askBirthday() throws IncorrectInputInScriptException {
        String strBirthday;
        LocalDate birthday;
        while (true) {
            try {
                Outputer.println("EnterBirthday");
                Outputer.print(App.PS2);
                strBirthday = userScanner.nextLine().trim();
                birthday = LocalDate.parse(strBirthday);
                break;
            }catch (DateTimeParseException exception){
                Outputer.printerror("BirthdayTimeParse");
            } catch (NoSuchElementException exception) {
                Outputer.printerror("BirthdayNotIdentifiedException");
            } catch (IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                System.exit(0);
            }
        }
        return birthday;
    }

    /**
     * Asks a user the height of group admin.
     * @return group admin height.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */

    public Long askHeight() throws IncorrectInputInScriptException {
        String strHeight;
        Long height;
        while (true) {
            try {
                Outputer.println("EnterHeight");
                Outputer.print(App.PS2);
                strHeight = userScanner.nextLine().trim();
                height = Long.parseLong(strHeight);
                if (height <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("HeightNotIdentifiedException");
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("HeightMustBeMoreZero");
            } catch (NumberFormatException exception) {
                Outputer.printerror("HeightMustBeNumber");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                System.exit(0);
            }
        }
        return height;
    }

    /**
     * Asks a user the weight of group admin.
     * @return group admin weight.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */

    public Double asWeight() throws IncorrectInputInScriptException {
        String strWeight;
        Double weight;
        while (true) {
            try {
                Outputer.println("EnterWeight");
                Outputer.print(App.PS2);
                strWeight = userScanner.nextLine().trim();
                weight = Double.parseDouble(strWeight);
                if (weight <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("WeightNotIdentifiedException");
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("WeightMustBeMoreZero");
            } catch (NumberFormatException exception) {
                Outputer.printerror("WeightMustBeNumber");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                System.exit(0);
            }
        }
        return weight;
    }

    /**
     * Asks a user the passport ID of group admin.
     * @return group admin passport ID.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */

    public String askPassportID() throws IncorrectInputInScriptException {
        String passportID;
        while (true) {
            try {
//                Outputer.println("enter passport number with length < " + StudyGroup.MAX_LENGTH + "и > " + StudyGroup.MIN_LENGTH);
                Outputer.println("EnterPassportId");
                Outputer.print(App.PS2);
                passportID = userScanner.nextLine().trim();
                if (passportID.equals("")) throw new MustBeNotEmptyException();
                if (passportID.length() < 5 || passportID.length() > 23) throw  new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("PassportNotIdentifiedException");
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("PassportMustBeMoreZero");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                System.exit(0);
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("PassportEmptyException");
            }
        }
        return passportID;
    }

    /**
     * Asks a user the group admin.
     * @return group admin.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */

    public Person askGroupAdmin() throws IncorrectInputInScriptException {
        String name;
        LocalDate birthday;
        Long height;
        Double weight;
        String passportID;

        name = askGroupAdminName();
        height = askHeight();
        weight = asWeight();
        passportID = askPassportID();
        birthday = askBirthday();

        return  new Person(name,birthday,height,weight,passportID);
    }

    /**
     * Asks a user a question.
     * @return Answer (true/false).
     * @param question A question.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */

    public boolean askQuestion(String question) throws IncorrectInputInScriptException {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                Outputer.println(finalQuestion);
                Outputer.print(App.PS2);
                answer = userScanner.nextLine().trim();
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("AnswerNotIndentifiedException");
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("AnswerLimitsException");
            } catch (IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                System.exit(0);
            }
        }
        return (answer.equals("+")) ? true : false;
    }

    @Override
    public String toString() {return "GroupAsker (helper class for user requests)";}
}
