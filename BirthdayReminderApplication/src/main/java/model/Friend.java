package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by ShahrozAliAzmat on 29/03/14.
 */
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Locale LOCALE = new Locale("da", "DK");
    private static final DateFormat DATEFORMAT = DateFormat.getDateInstance(
            DateFormat.SHORT, LOCALE);
    private String firstName;
    private String lastName;
    private GregorianCalendar birthday;

    public static final Comparator<Friend> FIRSTNAME_COMPARATOR = new Comparator<Friend>() {

        @Override
        public int compare(final Friend friend1, final Friend friend2) {
            return friend1.getFirstName().compareTo(friend2.getFirstName());
        }
    };

    public static final Comparator<Friend> LASTNAME_COMPARATOR = new Comparator<Friend>() {

        @Override
        public int compare(final Friend friend1, final Friend friend2) {
            return friend1.getLastName().compareTo(friend2.getLastName());
        }
    };

    public static final Comparator<Friend> BIRTHDAY_COMPARATOR = new Comparator<Friend>() {

        @Override
        public int compare(final Friend friend1, final Friend friend2) {

            return friend1.birthday.compareTo(friend2.birthday);
        }
    };

    public static final Comparator<Friend> BIRTHDAY_COMPARATOR_EXCLUDING_YEAR = new Comparator<Friend>() {

        @Override
        public int compare(final Friend friend1, final Friend friend2) {
            final int monthDifference = friend1.getMonth() - friend2.getMonth();
            if (monthDifference != 0) {
                return monthDifference;
            }
            return friend1.getDayOfMonth() - friend2.getDayOfMonth();
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GregorianCalendar getBirthday() {
        return birthday;
    }

    public void setBirthday(GregorianCalendar birthday) {
        this.birthday = birthday;
    }

    public int getYear() {
        return birthday.get(Calendar.YEAR);
    }

    /**
     * 0 based, 0 is January
     */
    public int getMonth() {
        return birthday.get(Calendar.MONTH);
    }

    public int getDayOfMonth() {
        return birthday.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result
                + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result
                + ((lastName == null) ? 0 : lastName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Friend other = (Friend) obj;
        if (birthday == null) {
            if (other.birthday != null)
                return false;
        } else if (!birthday.equals(other.birthday))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Friend [firstName=" + firstName + ", lastName=" + lastName
                + ", birthday=" + birthday + "]";
    }

}
