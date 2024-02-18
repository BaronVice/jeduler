package com.bv.pet.jeduler.entities;

public interface ApplicationEntity <ID extends Number> {
    ID getId();
    void setId(ID id);
}
