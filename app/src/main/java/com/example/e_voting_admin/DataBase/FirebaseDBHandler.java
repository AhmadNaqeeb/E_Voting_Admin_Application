package com.example.e_voting_admin.DataBase;

import android.app.Application;

import android.content.Context;
import android.util.Log;

import com.example.e_voting_admin.CandidatesModel.Candidates;
import com.example.e_voting_admin.Constituency_Manage.Constituency;
import com.example.e_voting_admin.Model.ConstituencyDataCallback;
import com.example.e_voting_admin.Model.DataRetrievalCallback;
import com.example.e_voting_admin.Model.CandidateDataCallback;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDBHandler extends Application {

    public static Firebase mRootReference;
    public static DataRetrievalCallback mCallback;
    public static CandidateDataCallback mCandidateDataCallback;
    public static ConstituencyDataCallback mConstituencyDataCallback;

    public static Context mContext ;
    @Override
    public void onCreate() {

        super.onCreate();
        Firebase.setAndroidContext(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mRootReference = new Firebase("https://e-voting-admin-76dc6-default-rtdb.firebaseio.com/EVotingDB");
        Log.d("Firebase DB Handler " , "Database Initialized");

    }

//    private static String FindNodePosition(String nodeName){
//
//        String nodePosition = null ;
//
//        switch (nodeName){
//
//            case "Users":
//                break ;
//
//            case "Accounts":
//                break ;
//
//            case "Leaves":
//
//                nodePosition = "2";
//
//                break ;
//
//            case "Leave_Approvals":
//
//                nodePosition = "3";
//
//                break ;
//
//            case "Leave_Balance":
//
//                nodePosition = "4";
//
//                break ;
//        }
//
//        return nodePosition ;
//    }

    public static boolean insert(String parentNodeName, String nodeName , Object node , String index){

        mRootReference.child(parentNodeName).child(nodeName).child(index).setValue(node);

        return true ;
    }

    public static boolean update(String parentNodeName, String nodeName , Object updatesNode , String childNodePosition){

        mRootReference.child(parentNodeName).child(nodeName).child(childNodePosition).setValue(updatesNode);

        return true ;
    }

    // Retrieve all children nodes.......

    public static void getNodeAllChildren(final String parentNodeName , final String nodeName){
        final Firebase db_ref = mRootReference.child(parentNodeName).child(nodeName);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Map<String , String>> nodeChildrenList = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Map<String,String> childMap = new HashMap<>();
                    childMap.put(data.getKey(),data.getValue().toString());
                    nodeChildrenList.add(childMap);
                    Log.d("FirebaseDBHandler: ", String.valueOf(data.getValue()));
                }
                Log.d("RetrieveDataSize: " , String.valueOf(nodeChildrenList.size()));
                mCallback.onDataRetrieved(nodeChildrenList , nodeName);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("cancelled: " , (parentNodeName + "," + nodeName));
            }
        };
        db_ref.addListenerForSingleValueEvent(valueEventListener);
        db_ref.removeEventListener(valueEventListener);
    }

    public static void getCandidateNodeAllChildren(final String parentNodeName , final String nodeName){
        final Firebase db_ref = mRootReference.child(parentNodeName).child(nodeName);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Candidates> nodeChildrenList = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Candidates candidates = data.getValue(Candidates.class);
                    Log.d("FirebaseDBHandler: ", candidates.toString());
                    nodeChildrenList.add(candidates);
                }
                Log.d("RetrieveDataSize: " , String.valueOf(nodeChildrenList.size()));
                mCandidateDataCallback.onCandidateDataRetrieved(nodeChildrenList,nodeName);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("cancelled: " , (parentNodeName + "," + nodeName));
            }
        };
        db_ref.addListenerForSingleValueEvent(valueEventListener);
        db_ref.removeEventListener(valueEventListener);
    }

    public static void getConstituteNodeAllChildren(final String parentNodeName , final String nodeName){
        final Firebase db_ref = mRootReference.child(parentNodeName).child(nodeName);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Constituency> nodeChildrenList = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Constituency constituency = data.getValue(Constituency.class);
                    Log.d("FirebaseDBHandler: ", constituency.toString());
                    nodeChildrenList.add(constituency);
                }
                Log.d("RetrieveDataSize: " , String.valueOf(nodeChildrenList.size()));
                mConstituencyDataCallback.onConstituencyDataRetrieved(nodeChildrenList,nodeName);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("cancelled: " , (parentNodeName + "," + nodeName));
            }
        };
        db_ref.addListenerForSingleValueEvent(valueEventListener);
        db_ref.removeEventListener(valueEventListener);
    }

//    public static void getCandidateNodeAllChildren(final String parentNodeName , final String nodeName){
//        final Firebase db_ref = mRootReference.child(parentNodeName).child(nodeName);
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<Map<String , String>> nodeChildrenList = new ArrayList<>();
//                for (DataSnapshot data : dataSnapshot.getChildren()){
//                    Map<String,String> childMap = new HashMap<>();
//                    int partyId = parseIdValue(data.getValue().toString(),EVotingDBContract.PARTIES_NODE);
//                    int constituencyId = parseIdValue(data.getValue().toString(),EVotingDBContract.CONSTITUENCY_NODE);
//
//                    String partyJsonString = searchNodeChildren(parentNodeName,EVotingDBContract.PARTIES_NODE,partyId);
//                    Log.d("FirebaseDBHandler: ", partyJsonString);
//                    String constituencyJsonString = searchNodeChildren(parentNodeName,EVotingDBContract.CONSTITUENCY_NODE,constituencyId);
//                    Log.d("FirebaseDBHandler: ", constituencyJsonString);
//                    childMap.put(data.getKey(),data.getValue().toString());
//                    childMap.put(EVotingDBContract.PARTIES_NODE,partyJsonString);
//                    childMap.put(EVotingDBContract.CONSTITUENCY_NODE,constituencyJsonString);
//                    nodeChildrenList.add(childMap);
//                }
//                Log.d("RetrieveDataSize: " , String.valueOf(nodeChildrenList.size()));
//                mCallback.onDataRetrieved(nodeChildrenList , nodeName);
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Log.d("cancelled: " , (parentNodeName + "," + nodeName));
//            }
//        };
//        db_ref.addListenerForSingleValueEvent(valueEventListener);
//        db_ref.removeEventListener(valueEventListener);
//    }

    private static int parseIdValue(String jsonString, String nodeName){
        int id = 0;
        String columnName = "";
        if (nodeName.equalsIgnoreCase(EVotingDBContract.PARTIES_NODE)){
            columnName=EVotingDBContract.CandidatesTable.CANDIDATE_PARTY_COLUMN;
        }else if (nodeName.equalsIgnoreCase(EVotingDBContract.CONSTITUENCY_NODE)){
            columnName=EVotingDBContract.CandidatesTable.CANDIDATE_CONSTITUENCY_COLUMN;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String partyId = jsonObject.get(columnName).toString();
            if (partyId != null && !partyId.isEmpty()){
                id = Integer.parseInt(partyId);
            }
            Log.d("FirebaseDBHandler: ", String.valueOf(id));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return id;
    }

    private static String searchNodeChildren(final String parentNodeName , final String nodeName, final int columnValue){
        final String[] jsonDataString = {""};
        final Firebase db_ref = mRootReference.child(parentNodeName).child(nodeName);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String jsonString = "";
                for (DataSnapshot data : dataSnapshot.getChildren()){

                    Log.d("FirebaseDBHandler: " , data.getValue().toString());
                    jsonString = data.getValue().toString();
                    int id = 0;
                    try {

                        JSONObject jsonObject = new JSONObject(jsonString);
                        String partyId = jsonObject.get(EVotingDBContract.PartiesTable.PARTY_ID_COLUMN).toString();

                        if (partyId != null && !partyId.isEmpty()){
                            id = Integer.parseInt(partyId);
                        }

                        if (id == columnValue){
                            jsonDataString[0] = jsonString;
                            break;
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                } // EOF for loop...
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("cancelled: " , (parentNodeName + "," + nodeName));
            }
        };
        db_ref.addListenerForSingleValueEvent(valueEventListener);
        db_ref.removeEventListener(valueEventListener);
        return jsonDataString[0];
    }

    // Add Listeners to check db Update or not.....

//    public static void FirebaseDBLeaveStatus(final String nodeName){
//
//        final String nodePosition = FindNodePosition(nodeName);
//
//        mRootReference.child(nodePosition).child(nodeName).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                Log.d("FDBH: " ,"onChildAdded");
//
//
//                Leave newLeave = dataSnapshot.getValue(Leave.class);
//                //ManagerHomeActivity.leaves.add(newLeave);
//                //Notification
//                if (SigninActivity.currUser.getUserRole().equals("LineManager-I"))
//                {
//                    if (newLeave.getLeaveStatus().equals("Submitted")) {
//                        Intent i = new Intent(mContext, ManagerHomeActivity.class);
//                        PendingIntent p = PendingIntent.getActivity(mContext, 0, i, 0);
//                        Notification myNotification = new NotificationCompat.Builder(mContext)
//                                .setContentTitle("Incoming Leave")
//                                .setContentText("New Leave Request from " + ManagerRecyclerViewAdapter.getUserName(newLeave.getUserid()))
//                                .setTicker("Notification !")
//                                .setSmallIcon(R.drawable.view_req)
//                                .addAction(R.drawable.icon, "Open Requests", p)
//                                .build();
//                        NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//                        nm.notify(1, myNotification);
//
//                    }
//
//                }
//                else  if (SigninActivity.currUser.getUserRole().equals("LineManager-II")) {
//                    if (newLeave.getLeaveStatus().equals("Pending")) {
//                        Intent i = new Intent(mContext,ManagerHomeActivity.class);
//                        PendingIntent p = PendingIntent.getActivity(mContext,0,i,0);
//                        Notification myNotification = new NotificationCompat.Builder(mContext)
//                                .setContentTitle("Incoming Leave")
//                                .setContentText("New Leave Request from " + ManagerRecyclerViewAdapter.getUserName(newLeave.getUserid()))
//                                .setTicker("Notification !")
//                                .setSmallIcon(R.drawable.icon)
//                                .addAction(R.drawable.icon,"Open Requests",p)
//                                .build();
//                        NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//                        nm.notify(1,myNotification);
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Log.d("FDBH: " ,"onChildChanged");
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }
//    public static void FirebaseDBLeaveApprovalStatus(final String nodeName){
//
//        final String nodePosition = FindNodePosition(nodeName);
//
//        mRootReference.child(nodePosition).child(nodeName).addChildEventListener(new ChildEventListener() {
//
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                Log.d("FDBH", "onChildAdded");
//                LeaveApprovals lp = dataSnapshot.getValue(LeaveApprovals.class);
//                //String userRole = ManagerRecyclerViewAdapter.getUserRole(lp.getUserid());
//
//
//                Log.d("id="," "+ lp.getLeaveID());
//                String status = getStatus(lp.getLeaveID());
//                if (status != null  && !status.equals("Submitted")) {
//
//                    EmployeeHomeActivity.leaveApprovalsList.add(lp);
//
//
//                    //Notification
//                    Intent i = new Intent(mContext, ViewRequestsActivity.class);
//                    PendingIntent p = PendingIntent.getActivity(mContext, 0, i, 0);
//                    Notification myNotification = new NotificationCompat.Builder(mContext)
//                            .setContentTitle("Leave Update")
//                            .setContentText("Your Leave Request Status Update")
//                            .setTicker("Notification !")
//                            .setSmallIcon(R.drawable.view_req)
//                            .addAction(R.drawable.icon , "Check Now", p)
//                            .build();
//
//                    NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//                    nm.notify(1, myNotification);
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }
//    private static  String getStatus(int id){
//
//        for (int i=0;i<ViewRequestsActivity.reqs.size();i++){
//            if(ViewRequestsActivity.reqs.get(i).getLeaveID()==id){
//                return ViewRequestsActivity.reqs.get(i).getLeaveStatus();
//            }
//        }
//        return null;
//    }
}
