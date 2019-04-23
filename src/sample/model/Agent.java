package sample.model;

public class Agent {
    private int AgentId;
    private String AgtFirstName;
    private String AgtMiddleInitial;
    private String AgtLastName;
    private String AgtBusPhone;
    private String AgtEmail;
    private String AgtPosition;
    private int AgtAgencyId;
    private String AgtAgencyName;
    private String AgtUser;
    private String AgtPassword;


    // Constructor for Agent
    public Agent(int agentId, String agtFirstName, String agtMiddleInitial, String agtLastName, String agtBusPhone, String agtEmail, String agtPosition, int agtAgencyId, String agtAgencyName, String agtUser, String agtPassword) {
        AgentId = agentId;
        AgtFirstName = agtFirstName;
        AgtMiddleInitial = agtMiddleInitial;
        AgtLastName = agtLastName;
        AgtBusPhone = agtBusPhone;
        AgtEmail = agtEmail;
        AgtPosition = agtPosition;
        AgtAgencyId = agtAgencyId;
        AgtAgencyName = agtAgencyName;
        AgtUser = agtUser;
        AgtPassword = agtPassword;
    }

    // Empty constructor
    public Agent() {

    }

    public int getAgentId() {
        return AgentId;
    }

    public void setAgentId(int agentId) {
        AgentId = agentId;
    }

    public String getAgtFirstName() {
        return AgtFirstName;
    }

    public void setAgtFirstName(String agtFirstName) {
        AgtFirstName = agtFirstName;
    }

    public String getAgtMiddleInitial() {
        return AgtMiddleInitial;
    }

    public void setAgtMiddleInitial(String agtMiddleInitial) {
        AgtMiddleInitial = agtMiddleInitial;
    }

    public String getAgtLastName() {
        return AgtLastName;
    }

    public void setAgtLastName(String agtLastName) {
        AgtLastName = agtLastName;
    }

    public String getAgtBusPhone() {
        return AgtBusPhone;
    }

    public void setAgtBusPhone(String agtBusPhone) {
        AgtBusPhone = agtBusPhone;
    }

    public String getAgtEmail() {
        return AgtEmail;
    }

    public void setAgtEmail(String agtEmail) {
        AgtEmail = agtEmail;
    }

    public String getAgtPosition() {
        return AgtPosition;
    }

    public void setAgtPosition(String agtPosition) {
        AgtPosition = agtPosition;
    }

    public int getAgtAgencyId() {
        return AgtAgencyId;
    }

    public void setAgtAgencyId(int agtAgencyId) {
        AgtAgencyId = agtAgencyId;
    }

    public String getAgtAgencyName() {
        return AgtAgencyName;
    }

    public void setAgtAgencyName(String agtAgencyName) {
        AgtAgencyName = agtAgencyName;
    }

    public String getAgtUser() {
        return AgtUser;
    }

    public void setAgtUser(String agtUser) {
        AgtUser = agtUser;
    }

    public String getAgtPassword() {
        return AgtPassword;
    }

    public void setAgtPassword(String agtPassword) {
        AgtPassword = agtPassword;
    }
}
