package jobScheduling.enumerate;

public enum JobStatusEnum {
    W(1, "Wait"),
    R(2,"Run"),
    F(3,"Finish");

    private final Integer code;
    private final String name;

    JobStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
