package toniwar.projects.fakephotoapp.presentation.listeners

interface FragmentListener {

    fun openFragment(flag: ActionFlag, arg: String?)

    companion object{
        enum class ActionFlag{
            MENU,
            CAMERA_X,
            GALLERY,
            EDITOR,
            FINAL_RESULT
        }
    }
}