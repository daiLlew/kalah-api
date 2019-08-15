package com.dai.llew.kalah.responses;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class GameCreated {

    private long id;
    private String uri;

    public GameCreated(long id) {
        this.id = id;
        // TODO hardcoded to localhost for now.
        this.uri = "http://localhost:8080/games/" + id;
    }

    public long getId() {
        return this.id;
    }

    public String getUri() {
        return this.uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        GameCreated that = (GameCreated) o;

        return new EqualsBuilder()
                .append(getId(), that.getId())
                .append(getUri(), that.getUri())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getUri())
                .toHashCode();
    }
}
