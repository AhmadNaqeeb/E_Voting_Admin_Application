package com.example.e_voting_admin.DataBase;

import android.net.Uri;

public final class EVotingDBContract {

    public static final String DATABASE_NAME = "EVotingDB";
    public static final String AUTHORITY = "com.example.e_voting_admin";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY) ;
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(DATABASE_NAME).build();

    public static final String ADMIN_NODE = "Admin" ;
    public static final String PARTIES_NODE = "Parties" ;
    public static final String CANDIDATE_NODE = "Candidates" ;
    public static final String CONSTITUENCY_NODE = "Constituency" ;


    // Default Contructor.....

    public EVotingDBContract(){

    }

    // Inner classes act as provider of Database table information.....

    // Party table in EVotingDB Database........

    public static class AdminTable{
        public static String TABLE_NAME = "admin";
        public static String ADMIN_ID_COLUMN = "id";
        public static String ADMIN_CNIC_COLUMN = "cnic";
        public static String ADMIN_NAME_COLUMN = "name";
        public static String ADMIN_PASSWORD_COLUMN = "password";

    }


    public static class PartiesTable{

        public static String TABLE_NAME = "Parties";
        public static String PARTY_ID_COLUMN = "id";
        public static String PARTY_NAME_COLUMN = "name";
        public static String PARTY_SYMBOL_COLUMN = "symbol";

    }
    public static class CandidatesTable {

        public static String TABLE_NAME = "Candidates";
        public static String CANDIDATE_ID_COLUMN = "id";
        public static String CANDIDATE_CNIC_COLUMN = "cnic";
        public static String CANDIDATE_PROVINCE_COLUMN = "province";
        public static String CANDIDATE_AREA_COLUMN = "area";
        public static String CANDIDATE_NAME_COLUMN = "name";
        public static String CANDIDATE_CITY_COLUMN = "city";
        public static String CANDIDATE_CONSTITUENCY_COLUMN = "constituency";
        public static String CANDIDATE_PARTY_COLUMN = "party";
    }

    public static class ConstituencyTable {

        public static String TABLE_NAME = "Constituency";
        public static String CONSTITUENCY_ID_COLUMN = "id";
        public static String CONSTITUENCY_NAME_COLUMN = "name";
        public static String CONSTITUENCY_PARENT_COLUMN = "parent";
        public static String CONSTITUENCY_CITY_COLUMN = "city";
        public static String CONSTITUENCY_PROVINCE_COLUMN = "province";
        public static String CONSTITUENCY_AREA_COLUMN = "area";
    }

    // Users Accounts table in FacultyDB Database........
//    public static class AccountsTable{
//
//        public static String TABLE_NAME = "Accounts";
//        public static String USER_ID_COLUMN = "userid";
//        public static String ACCOUNT_ID_COLUMN = "accountID";
//        public static String USER_PASSWORD_COLUMN = "userPassword";
//    }

    // Leaves table in FacultyDB Database........
//    public static class LeavesTable{
//
//        public static String TABLE_NAME = "Leaves";
//        public static String LEAVE_ID_COLUMN = "leaveID";
//        public static String LEAVE_TYPE_COLUMN = "leaveType";
//        public static String LEAVE_STATUS_COLUMN = "leaveStatus";
//        public static String LEAVE_START_DATE_COLUMN = "leaveStartDate";
//        public static String LEAVE_END_DATE_COLUMN = "leaveEndDate";
//        public static String LEAVE_DESCRIPTION_COLUMN = "leaveDescription";
//        public static String USER_ID_COLUMN = "userid";
//
//    }

    // Leave Approvals table in FacultyDB Database........
//    public static class LeaveApprovalsTable{
//
//        public static String TABLE_NAME = "Leave_Approvals";
//        public static String LEAVE_ID_COLUMN = "leaveID";
//        public static String LEAVE_APPROVALS_ID_COLUMN = "leaveApprovalsID";
//        public static String APPROVAL_ACTION_COLUMN = "approvalAction";
//        public static String APPROVAL_TIME_COLUMN = "approvalTime";
//        public static String APPROVAL_COMMENTS_COLUMN = "approvalComments";
//        public static String USER_ID_COLUMN = "userid";
//    }

    // Leave Balance table in FacultyDB Database........
//    public static class LeaveBalanceTable{
//
//        public static String TABLE_NAME = "Leave_Balance";
//        public static String LEAVE_Balance_ID_COLUMN = "leaveBalanceId";
//        public static String LEAVE_TOTAL_COLUMN = "leaveTotal";
//        public static String LEAVE_USED_COLUMN = "leaveUsed";
//        public static String LEAVE_TYPE_COLUMN = "leaveType";
//        public static String USER_ID_COLUMN = "userid";
//    }

    // Employee Attendance table in FacultyDB Database........
//    public static class EmployeeAttendanceTable{
//
//        public static String TABLE_NAME = "Attendance";
//        public static String ATTENDANCE_ID_COLUMN = "attenID";
//        public static String ATTENDANCE_DATE_COLUMN = "attenDate";
//        public static String ATTENDANCE_TIME_IN_COLUMN = "TimeIn";
//        public static String ATTENDANCE_TIME_OUT_COLUMN = "TimeOut";
//        public static String USER_ID_COLUMN = "userid";
//    }
}
