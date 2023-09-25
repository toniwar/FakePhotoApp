package toniwar.projects.fakephotoapp.domain.use_cases.data_use_cases

import toniwar.projects.fakephotoapp.Constants
import toniwar.projects.fakephotoapp.domain.repositories.DataRepository

class ReadFromSharedPrefsUseCase(private val dataRepository: DataRepository) {

    fun <T> readFromSharedPrefs(name: Constants.PrefDataType): T?{
        return dataRepository.readFromSharedPrefs(name)
    }

}
