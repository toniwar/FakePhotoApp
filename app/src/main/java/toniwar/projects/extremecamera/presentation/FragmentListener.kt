package toniwar.projects.extremecamera.presentation

interface FragmentListener {

    fun openFragment(flag: ActionFlag, arg: String?)

    companion object{
        enum class ActionFlag{
            MENU,
            CAMERA_X,
            GALLERY,
            EDITOR
        }
    }
}