package com.lunova.moonbot.core.api.plugin.features.settings.validation;

import com.lunova.moonbot.core.api.plugin.features.settings.input.DataType;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.rules.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Additional possible validation rules
 * UniquenessContent: Validates that the string is unique within a certain context or dataset (e.g., username, email).
 * ContentValidationRule: More complex content validations that might involve checking against a database or third-party service (e.g., profanity filter, copyright material).
 *
 */
public class StringValidation extends Validation<String> {

    private StringValidation(Builder builder) {
        super(DataType.STRING, builder.rules);
    }

    public static class Builder {

        private final List<ValidationRule<String>> rules = new ArrayList<>();

        public Builder setMinimumLength(int minimum) {
            rules.add(new MinimumValueRule<>(minimum) {
                @Override
                public boolean validateRule(String target) {
                    return target.length() >= getValue().intValue();
                }
            });
            return this;
        }

        public Builder setMaximumLength(int maximum) {
            rules.add(new MaximumValueRule<>(maximum) {
                @Override
                public boolean validateRule(String target) {
                    return target.length() <= getValue().intValue();
                }
            });
            return this;
        }

        public Builder setRangeLength(int minimum, int maximum) {
            rules.add(new RangeValueRule<>(minimum, maximum) {
                @Override
                public boolean validateRule(String target) {
                    return target.length() >= getMin().intValue() && target.length() <= getMax().intValue();
                }
            });
            return this;
        }

        public Builder setRegex(String regex) {
            rules.add(new RegexValidationRule(regex));
            return this;
        }

        public StringValidation build() {
            return new StringValidation(this);
        }
    }

    @Override
    public boolean validateTarget(String target) {
        return getRules().stream().allMatch(rule -> rule.validateRule(target));
    }


}
