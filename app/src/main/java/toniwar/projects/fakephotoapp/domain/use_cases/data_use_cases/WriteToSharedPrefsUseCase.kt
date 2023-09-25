package toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases

import toniwar.projects.fakephotoapp.Constants
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository

class WriteToSharedPrefsUseCase(private val dataRepository: DataRepository) {

    fun <T> writeToSharedPrefs(name: Constants.PrefDataType,data: T){
        dataRepository.writeToSharedPrefs(name, data)
    }
}
