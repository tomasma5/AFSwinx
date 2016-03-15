﻿using AFWindowsPhone.builders.components.parts.validators;
using AFWindowsPhone.enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using AFWindowsPhone.components.parts.validators;
using AFWindowsPhone.utils;

namespace AFWindowsPhone.builders.components.parts
{
    class ValidatorFactory
    {
        private static ValidatorFactory instance = null;


        public static ValidatorFactory getInstance()
        {
            if (instance == null)
            {
                instance = new ValidatorFactory();
            }
            return instance;
        }

        public AFValidator getValidator(ValidationRule rule)
        {
            if (rule.getValidationType().Equals(SupportedValidations.REQUIRED.getValidationType()) && Utils.TryToConvertIntoBoolean(rule.getValue()))
            {
                return new RequiredValidator();
            }
            if (rule.getValidationType().Equals(SupportedValidations.MAXLENGTH.getValidationType()))
            {
                return new MaxCharsValidator();
            }
            if (rule.getValidationType().Equals(SupportedValidations.MAX.getValidationType()))
            {
                return new MaxValueValidator();
            }
            if (rule.getValidationType().Equals(SupportedValidations.MIN.getValidationType()))
            {
                return new MinValueValidator();
            }
            if (rule.getValidationType().Equals(SupportedValidations.LESSTHAN.getValidationType()))
            {
                return new LessThanValidator();
            }
            if (rule.getValidationType().Equals(SupportedValidations.NUMBER.getValidationType()))
            {
                return new NumberValidator();
            }
            //not found
            return null;
        }

    }
}
