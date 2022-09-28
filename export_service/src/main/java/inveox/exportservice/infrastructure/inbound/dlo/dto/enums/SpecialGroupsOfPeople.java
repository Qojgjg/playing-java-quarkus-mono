package inveox.exportservice.infrastructure.inbound.dlo.dto.enums;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SpecialGroupsOfPeople {
    NO_SPECIAL_GROUP("00"),
    BSHG("04"),
    BVG("06"),
    SVA_MARKING_INTERGOVERNMENTAL("07"),
    SVA_MARKING_FLAT_RATE("08"),
    RECIPIENTS_OF_ASYLBLG("09");

    private static final Map<String, SpecialGroupsOfPeople> groupsByCode = Stream.of(SpecialGroupsOfPeople.values())
            .collect(Collectors.toMap(SpecialGroupsOfPeople::getCode, Function.identity()));

    public static SpecialGroupsOfPeople ofCode(String code) {
        return groupsByCode.get(code);
    }

    private final String code;
}
