package com.bv.pet.jeduler.config.carriers;

import com.bv.pet.jeduler.services.mock.generators.CategoryGenerator;
import com.bv.pet.jeduler.services.mock.generators.UserGenerator;

public record Generators(
        UserGenerator userGenerator,
        CategoryGenerator categoryGenerator
) {
}
