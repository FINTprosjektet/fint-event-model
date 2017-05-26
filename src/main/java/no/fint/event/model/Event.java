package no.fint.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * An Event object is created at the consumer interface and sent back to either the cache or the provider. After the
 * event is processed it is sent back to the consumer interface. The Event object is at part of the
 * eventually consistent pattern the <b>felleskomponent</b> is build on.
 *
 * @param <T> Type of the objects in the {@link #data} field
 */
@EqualsAndHashCode
public class Event<T> implements Serializable {
    /**
     * A unique id for an event. It should be a UUID generated by <code>UUID.randomUUID().toString()</code>
     */
    private String corrId;
    /**
     * This should be the command we wish to execute at the provider or cache.
     * For example it could be <code>GET_ALL_EMPLOYEES</code>
     */
    private String action;
    /**
     * Status of the event. See {@link Status} for more informasjon.
     */
    private Status status;
    /**
     * The time of the creation of the event. It should be a Unix timestamp generated by <code>System.currentTimeMillis()</code>
     */
    private long time;
    /**
     * Id of the organization the event is from.
     */
    private String orgId;
    /**
     * The name of the <em>felleskomponent</em> the event is for.
     */
    private String source;
    /**
     * The name of the client who generated the event. It should either be the name associated the the API token or
     * <b>CACHE</b>
     */
    private String client;
    /**
     * This message should be set if something goes wrong. It should typically describe what went wrong,
     * for example a stack trace or an error message.
     */
    private String message;
    /**
     * This is the list of payload/data for the event.
     * <ul>
     * <li>For inbound events it should be queryfilters or data to be updated.</li>
     * <li>For outbound events it should be the data requested or the status of the update command</li>
     * </ul>
     */
    private List<T> data;

    /**
     * Default constructor that create an empty Event object.
     */
    public Event() {
        this.data = new ArrayList<>();
    }

    /**
     * Constructor that copies values from the input Event object into the new Event object.
     *
     * @param event contains values that will be copied into the new instance
     */
    @SuppressWarnings("unchecked")
    public Event(Event event) {
        this.corrId = event.getCorrId();
        this.action = event.getAction();
        this.status = event.getStatus();
        this.time = event.getTime();
        this.orgId = event.getOrgId();
        this.source = event.getSource();
        this.client = event.getClient();
        this.message = event.getMessage();
        this.data = event.getData();
    }

    /**
     * Constructor that sets up an {@link Status#NEW} Event.
     *
     * @param orgId  See {@link #orgId} for more information.
     * @param source See {@link #source} for more information.
     * @param action See {@link #action} for more information.
     * @param client See {@link #client} for more information.
     */
    public Event(String orgId, String source, String action, String client) {
        this.orgId = orgId;
        this.source = source;
        this.action = action;
        this.client = client;

        this.status = Status.NEW;
        this.corrId = UUID.randomUUID().toString();
        this.time = System.currentTimeMillis();
        this.data = new ArrayList<>();
    }

    /**
     * @return {@link #corrId}
     */
    public String getCorrId() {
        return corrId;
    }

    /**
     * @param corrId See {@link #corrId} for more information.
     */
    public void setCorrId(String corrId) {
        this.corrId = corrId;
    }

    /**
     * @return {@link #action}
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action See {@link #action} for more information.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return {@link #status}
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status See {@link Status} for more information.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return {@link #time}
     */
    public long getTime() {
        return time;
    }

    /**
     * @param time See {@link #time} for more information.
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * @return {@link #orgId}
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * @param orgId See {@link #orgId} for more information.
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * @return {@link #source}
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source See {@link #source} for more information.
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return {@link #client}
     */
    public String getClient() {
        return client;
    }

    /**
     * @param client See {@link #client} for more information.
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * @return {@link #message}
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message See {@link #message} for more information.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return {@link #data}
     */
    public List<T> getData() {
        return data;
    }

    /**
     * @param data See {@link #data} for more information.
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * Adds data to the ArrayList.
     *
     * @param data See {@link #data} for more information.
     */
    public void addData(T data) {
        this.data.add(data);
    }

    /**
     * Return true if the event has action HEALTH.
     * Is not included in the json representation.
     *
     * @return true if the event has action HEALTH
     */
    @JsonIgnore
    public boolean isHealthCheck() {
        return DefaultActions.HEALTH.equals(action);
    }

    /**
     * @return JSON String of the Event object.
     */
    @Override
    public String toString() {
        return "Event{" +
                "corrId='" + corrId + '\'' +
                ", action='" + action + '\'' +
                ", status=" + status +
                ", time=" + time +
                ", orgId='" + orgId + '\'' +
                ", source='" + source + '\'' +
                ", client='" + client + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}