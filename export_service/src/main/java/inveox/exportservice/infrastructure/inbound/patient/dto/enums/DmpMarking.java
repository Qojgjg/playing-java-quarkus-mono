package inveox.exportservice.infrastructure.inbound.patient.dto.enums;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DmpMarking {
    NO_SPECIAL_GROUP("00"),
    TYPE_2_DIABETES("01"),
    BREAST_CANCER("02"),
    CORONARY_HEART_DISEASE("03"),
    TYPE_1_DIABETES("04"),
    BRONCHIAL_ASTHMA("05"),
    COPD("06"),
    CHRONIC_HEART_FAILURE("07"),
    DEPRESSION("08"),
    BACK_PAIN("09");

    private static final Map<String, DmpMarking> dmpMarkingByCode = Stream.of(DmpMarking.values())
            .collect(Collectors.toMap(DmpMarking::getCode, Function.identity()));

    public static DmpMarking ofCode(String code) {
        return dmpMarkingByCode.get(code);
    }

    private final String code;
}