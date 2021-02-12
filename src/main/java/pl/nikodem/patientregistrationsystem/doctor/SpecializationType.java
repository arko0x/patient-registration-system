package pl.nikodem.patientregistrationsystem.doctor;

public enum SpecializationType {
    ALLERGY_AND_IMMUNOLOGY("Allergy and Immunology"),
    ANESTHESIOLOGY("Anesthesiology"),
    DERMATOLOGY("Dermatology"),
    DIAGNOSTIC("Diagnostic"),
    RADIOLOGY("Radiology"),
    EMERGENCY_MEDICINE("Emergency Medicine"),
    FAMILY_MEDICINE("Family Medicine"),
    INTERNAL_MEDICINE("Internal Medicine"),
    MEDICAL_GENETICS("Medical Genetics"),
    NEUROLOGY("Neurology"),
    NUCLEAR_MEDICINE("Nuclear Medicine"),
    OBSTETRICS_AND_GYNECOLOGY("Obstetrics and Gynecology"),
    OPHTHALMOLOGY("Ophthalmology"),
    PATHOLOGY("Pathology"),
    PEDIATRICS("Pediatrics"),
    PHYSICAL_MEDICINE_AND_REHABILITATION("Physical Medicine and Rehabilitation"),
    PREVENTIVE_MEDICINE("Preventive Medicine"),
    PSYCHIATRY("Psychiatry"),
    RADIATION_ONCOLOGY("Radiation Oncology"),
    SURGERY("Surgery"),
    UROLOGY("Urology");

    String name;

    SpecializationType(String name) {
        this.name = name;
    }
}
