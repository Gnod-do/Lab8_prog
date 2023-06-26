package common.interaction;

import common.data.StudyGroup;


import java.io.Serializable;
import java.util.LinkedList;

public class Response implements Serializable {

    LinkedList<StudyGroup> groupsCollection;
    private ResponseCode responseCode;

    private String responseBody;

    private String[] responseBodyArgs;





    public Response(ResponseCode responseCode, String responseBody, String[] responseBodyArgs,
                    LinkedList<StudyGroup> groupsCollection) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
        this.groupsCollection = groupsCollection;
        this.responseBodyArgs = responseBodyArgs;

    }

    /**
     * @return Response сode.
     */

    public ResponseCode getResponseCode() {return responseCode;}

    /**
     * @return Response body.
     */

    public String getResponseBody() {return responseBody;}

    public String[] getResponseBodyArgs() {
        return responseBodyArgs;
    }

    public LinkedList<StudyGroup> getGroupsCollection() {
        return groupsCollection;
    }

    @Override
    public String toString() {return "Response[" + responseCode + ", " + responseBody + "]";}

}
