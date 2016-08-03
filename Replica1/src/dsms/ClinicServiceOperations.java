package dsms;

/**
 * Interface definition: ClinicService.
 * 
 * @author OpenORB Compiler
 */
public interface ClinicServiceOperations
{
    /**
     * Operation createDRecord
     */
    public String createDRecord(String firstName, String lastName, String address, String phone, String specialization, String location, String managerID);

    /**
     * Operation createNRecord
     */
    public String createNRecord(String firstName, String lastName, String designation, String status, String statusDate, String managerID);

    /**
     * Operation getRecordCounts
     */
    public String getRecordCounts(String recordType, String managerID);

    /**
     * Operation editRecord
     */
    public String editRecord(String recordID, String fieldName, String newValue, String managerID);

    /**
     * Operation transferRecord
     */
    public String transferRecord(String recordID, String remoteClinicServerName, String managerID);

}
