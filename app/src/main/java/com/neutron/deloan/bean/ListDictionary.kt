package com.neutron.deloan.bean

import com.neutron.deloan.R
import com.neutron.deloan.utils.UIUtils


class ListDictionary {


    companion object {

        val gender_Male = UIUtils.getString(R.string.gender_Male)
        val gender_Female = UIUtils.getString(R.string.gender_Female)
        val marital_status_single = UIUtils.getString(R.string.marital_status_single)
        val marital_status_married = UIUtils.getString(R.string.marital_status_married)
        val marital_status_divorced = UIUtils.getString(R.string.marital_status_divorced)
        val marital_status_widowed = UIUtils.getString(R.string.marital_status_widowed)
        val dic_primary_school = UIUtils.getString(R.string.dic_primary_school)
        val dic_junior_middle_school = UIUtils.getString(R.string.dic_junior_middle_school)
        val dic_high_school = UIUtils.getString(R.string.dic_high_school)
        val dic_junior_college = UIUtils.getString(R.string.dic_junior_college)
        val dic_undergraduate = UIUtils.getString(R.string.dic_undergraduate)
        val dic_communication = UIUtils.getString(R.string.dic_communication)
        val dic_children_four = UIUtils.getString(R.string.dic_children_four)
        val dic_children_four_or_more = UIUtils.getString(R.string.dic_children_four_or_more)
        val dic_children_none = UIUtils.getString(R.string.dic_children_none)
        val dic_children_one = UIUtils.getString(R.string.dic_children_one)
        val dic_children_three = UIUtils.getString(R.string.dic_children_three)
        val dic_children_two = UIUtils.getString(R.string.dic_children_two)
        val dic_renting = UIUtils.getString(R.string.dic_renting)
        val dic_owned_housing_in_loan = UIUtils.getString(R.string.dic_owned_housing_in_loan)
        val dic_owned_housing_without_loans =
            UIUtils.getString(R.string.dic_owned_housing_without_loans)
        val dic_live_with_parents = UIUtils.getString(R.string.dic_live_with_parents)
        val dic_provided_by_the_unit = UIUtils.getString(R.string.dic_provided_by_the_unit)
        val dic_three_months = UIUtils.getString(R.string.dic_three_months)
        val dic_six_months = UIUtils.getString(R.string.dic_six_months)
        val dic_one_year = UIUtils.getString(R.string.dic_one_year)
        val dic_two_year = UIUtils.getString(R.string.dic_two_year)
        val dic_two_year_or_more = UIUtils.getString(R.string.dic_two_year_or_more)

        val dic_father = UIUtils.getString(R.string.dic_father)
        val dic_mother = UIUtils.getString(R.string.dic_mother)
        val dic_daughter = UIUtils.getString(R.string.dic_daughter)

        val dic_son = UIUtils.getString(R.string.dic_son)

        val dic_spouse = UIUtils.getString(R.string.dic_spouse)

        val dic_uncle_and_aunt = UIUtils.getString(R.string.dic_uncle_and_aunt)
        val dic_brothers = UIUtils.getString(R.string.dic_brothers)
        val dic_leadership = UIUtils.getString(R.string.dic_leadership)
        val dic_sisters = UIUtils.getString(R.string.dic_sisters)
        val dic_nephew_and_niece = UIUtils.getString(R.string.dic_nephew_and_niece)
        val dic_cousin = UIUtils.getString(R.string.dic_cousin)
        val dic_classmate = UIUtils.getString(R.string.dic_classmate)
        val dic_colleague = UIUtils.getString(R.string.dic_colleague)
        val dic_friend = UIUtils.getString(R.string.dic_friend)
        val dic_eight_thousand_or_less = UIUtils.getString(R.string.dic_eight_thousand_or_less)
        val dic_fifteen_thousand_or_more = UIUtils.getString(R.string.dic_fifteen_thousand_or_more)
        val dic_between_eight_thousand_and_fifteen_thousand =
            UIUtils.getString(R.string.dic_between_eight_thousand_and_fifteen_thousand)
        val dic_regular_employees_less_than_two_years =
            UIUtils.getString(R.string.dic_regular_employees_less_than_two_years)
        val dic_regular_employees_more_than_two_years =
            UIUtils.getString(R.string.dic_regular_employees_more_than_two_years)
        val dic_manager = UIUtils.getString(R.string.dic_manager)
        val dic_executives = UIUtils.getString(R.string.dic_executives)
        val dic_outsourcing = UIUtils.getString(R.string.dic_outsourcing)
        val dic_temporary_contract_worker =
            UIUtils.getString(R.string.dic_temporary_contract_worker)
        val dic_private_owners = UIUtils.getString(R.string.dic_private_owners)
        val dic_office_worker = UIUtils.getString(R.string.dic_office_worker)
        val dic_freelance_motorcycle_driver_or_farmer_or_fisherman =
            UIUtils.getString(R.string.dic_freelance_motorcycle_driver_or_farmer_or_fisherman)
        val dic_school_student = UIUtils.getString(R.string.dic_school_student)
        val dic_retired_unemployed_housewife =
            UIUtils.getString(R.string.dic_retired_unemployed_housewife)
        val dic_financial = UIUtils.getString(R.string.dic_financial)
        val dic_agriculture_forestry_fishery_and_animal_husbandry =
            UIUtils.getString(R.string.dic_agriculture_forestry_fishery_and_animal_husbandry)
        val dic_manufacturing = UIUtils.getString(R.string.dic_manufacturing)
        val dic_tourism = UIUtils.getString(R.string.dic_tourism)
        val dic_trading = UIUtils.getString(R.string.dic_trading)
        val dic_real_estate = UIUtils.getString(R.string.dic_real_estate)
        val dic_internet = UIUtils.getString(R.string.dic_internet)
        val dic_government = UIUtils.getString(R.string.dic_government)
        val dic_industry_other = UIUtils.getString(R.string.dic_industry_other)
        val dic_education = UIUtils.getString(R.string.dic_education)
        val dic_hydropower_oil_and_gas = UIUtils.getString(R.string.dic_hydropower_oil_and_gas)
        val dic_public_security_bureau = UIUtils.getString(R.string.dic_public_security_bureau)
        val dic_military_organization = UIUtils.getString(R.string.dic_military_organization)
        val dic_food = UIUtils.getString(R.string.dic_food)
        val dic_media_advertising = UIUtils.getString(R.string.dic_media_advertising)
        val dic_traffic = UIUtils.getString(R.string.dic_traffic)
        val dic_legal = UIUtils.getString(R.string.dic_legal)
        val dic_retail = UIUtils.getString(R.string.dic_retail)
        val lend_medical_institutions = UIUtils.getString(R.string.lend_medical_institutions)

        val day_pay = UIUtils.getString(R.string.day_pay)
        val week_pay = UIUtils.getString(R.string.week_pay)
        val month_pay = UIUtils.getString(R.string.month_pay)


        val genderList = listOf(gender_Male, gender_Female)

        val homeTypeList = listOf(
            dic_renting,
            dic_owned_housing_in_loan,
            dic_owned_housing_without_loans,
            dic_live_with_parents,
            dic_provided_by_the_unit,
            dic_communication
        )


        val homeTimeList = listOf(
            dic_three_months,
            dic_six_months,
            dic_one_year,
            dic_two_year,
            dic_two_year_or_more
        )


        val culturalList = listOf(
            dic_primary_school,
            dic_junior_middle_school,
            dic_high_school,
            dic_junior_college,
            dic_undergraduate,
            dic_communication
        )


        val MaritalStatusList = listOf(
            marital_status_single,
            marital_status_married,
            marital_status_divorced,
            marital_status_widowed
        )

        val HowChildrenList = listOf(
            dic_children_none,
            dic_children_one,
            dic_children_two,
            dic_children_three,
            dic_children_four,
            dic_children_four_or_more
        )


        val identityList = listOf(
            dic_regular_employees_less_than_two_years,
            dic_regular_employees_more_than_two_years,
            dic_manager,
            dic_executives,
            dic_outsourcing,
            dic_temporary_contract_worker

        )


        val companyTypeList = listOf(
            dic_financial,
            dic_agriculture_forestry_fishery_and_animal_husbandry,
            dic_manufacturing,
            dic_trading,
            dic_real_estate,
            dic_internet,
            dic_government,
            dic_industry_other,
            dic_education,
            lend_medical_institutions,
            dic_tourism,
            dic_hydropower_oil_and_gas,
            dic_public_security_bureau,
            dic_military_organization,
            dic_food,
            dic_media_advertising,
            dic_traffic,
            dic_legal,
            dic_retail,
            dic_communication
        )

        val OccupationTypeList = listOf(
            dic_private_owners,
            dic_office_worker,
            dic_freelance_motorcycle_driver_or_farmer_or_fisherman,
            dic_retired_unemployed_housewife
        )

        val incomeRangeList = listOf(
            dic_eight_thousand_or_less,
            dic_between_eight_thousand_and_fifteen_thousand,
            dic_fifteen_thousand_or_more
        )


        val contactFirstList = listOf(
            dic_father,
            dic_mother,
            dic_spouse,
            dic_son,
            dic_daughter,
            dic_brothers,
            dic_sisters,
            dic_nephew_and_niece,
            dic_cousin,
            dic_uncle_and_aunt

        )

        val contactTwoList = listOf(
            dic_classmate,
            dic_colleague,
            dic_friend


        )

//        val contactFirstList = listOf(
//            dic_father,
////            dic_mother,
//            dic_spouse,
//            dic_son
////            dic_daughter
//

//        )

//        val contactTwoList = listOf(
//            dic_classmate,
//            dic_colleague,
//            dic_friend
//        )

        val contactTreeList = listOf(
//            dic_friend,
//            dic_colleague,
//            dic_leadership
            dic_father,
            dic_mother,
            dic_spouse,
            dic_son,
            dic_daughter,
            dic_brothers,
            dic_sisters,
            dic_nephew_and_niece,
            dic_cousin,
            dic_classmate,
            dic_colleague,
            dic_friend,
            dic_uncle_and_aunt
        )


        val DatePayList = listOf(
            day_pay,
            week_pay,
            month_pay
        )


    }


}