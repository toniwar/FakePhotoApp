package toniwar.projects.extreamcamera.presentation

interface FragmentListener {

    fun openFragment(flag: ActionFlag)

    companion object{
        enum class ActionFlag{
            MENU,
            NATURAL_DISASTERS,
            PARANORMAL
        }
    }
}