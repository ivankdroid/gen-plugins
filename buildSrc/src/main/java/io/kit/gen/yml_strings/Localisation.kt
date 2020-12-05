package io.kit.gen.yml_strings

data class Localisation(
    var screens: Map<String, Res> = emptyMap()
) {
    data class Res(
        var strings: Map<String, String> = emptyMap(),
        var plurals: Map<String, Plural> = emptyMap(),
        var arrays: Map<String,List<String>> = emptyMap()
    )

    data class Plural(
        var zero: String? = null,
        var one: String? = null,
        var two: String? = null,
        var few: String? = null,
        var many: String? = null,
        var other: String? = null
    )
}