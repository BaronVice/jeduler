package com.bv.pet.jeduler.services.populate.generators;

import java.util.List;

public interface Generator <T> {
    List<T> generate(int amount);
}
