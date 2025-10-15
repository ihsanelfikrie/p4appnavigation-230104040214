package id.antasari.p4appnavigation_230104040214.nav

sealed class Route(val path: String) {
    data object Home : Route("home")

    // Activity A/B (Explicit Intent)
    data object ActivityA : Route("activity_a")
    data object ActivityB : Route("activity_b")

    // Activity C/D (Send Data)
    data object ActivityC : Route("activity_c")
    data object ActivityD : Route("activity_d/{name}/{nim}") {
        fun of(name: String, nim: String): String {
            return "activity_d/$name/$nim"
        }
    }

    // Back Stack Demo
    data object Step1 : Route("step1")
    data object Step2 : Route("step2")
    data object Step3 : Route("step3")

    // Hub (Activity + Fragment)
    data object Hub : Route("hub")
    data object HubDashboard : Route("hub/dashboard")
    data object HubMessages : Route("hub/messages")
    data object HubMessageDetail : Route("hub/messages/detail/{id}") {
        fun of(id: String): String {
            return "hub/messages/detail/$id"
        }
    }
    data object HubProfile : Route("hub/profile")
}