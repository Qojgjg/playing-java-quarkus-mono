package inveox.exportservice.infrastructure.inbound.dlo.dto.enums;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TypeOfInsured {
    MEMBER("1"),
    FAMILY_MEMBER("3"),
    PENSIONER("5");

    private static final Map<String, TypeOfInsured> typeByCode = Stream.of(TypeOfInsured.values())
            .collect(Collectors.toMap(TypeOfInsured::getCode, Function.identity()));

    public static TypeOfInsured ofCode(String code) {
        return typeByCode.get(code);
    }

    private final String code;
}