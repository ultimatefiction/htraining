package com.veritas.htraining.model;

import com.google.protobuf.InvalidProtocolBufferException;
import com.veritas.htraining.UserProto;

public class Permission {

    private String domain;
    private int level;

    public Permission(String domain, int level) {
        this.domain = domain;
        this.level = level;
    }

    public Permission(byte[] bytes) throws InvalidProtocolBufferException {
        UserProto.User.Permission message = UserProto.User.Permission.parseFrom(bytes);
        this.domain = message.getDomain();
        this.level = message.getLevel();
    }

    public UserProto.User.Permission getProtoMessage() {
        return UserProto.User.Permission.newBuilder()
                .setDomain(domain)
                .setLevel(level)
                .build();
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return String.format("{%s|%d}", domain, level);
    }
}
