package entities;

import lombok.Data;

import java.util.Objects;

@Data
public class ProjectsTestObjects {
    private String name;
    private String method;
    private String status;
    private String startTime;
    private String endTime;
    private String duration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectsTestObjects tablePojo = (ProjectsTestObjects) o;
        return name.equalsIgnoreCase(tablePojo.name) && method.equalsIgnoreCase(tablePojo.method) &&
                status.equalsIgnoreCase(tablePojo.status) && Objects.equals(startTime, tablePojo.startTime) &&
                Objects.equals(endTime, tablePojo.endTime) && Objects.equals(duration, tablePojo.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, method, status, startTime, endTime, duration);
    }
}