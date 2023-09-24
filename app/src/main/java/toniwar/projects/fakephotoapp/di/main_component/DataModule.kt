package toniwar.projects.fakephotoapp.di.main_component


import android.content.Context
import dagger.Module
import dagger.Provides
import toniwar.projects.fakephotoapp.data.database.AppDataBase
import toniwar.projects.fakephotoapp.data.database.ClipArtsDAO
import toniwar.projects.fakephotoapp.data.network.RetrofitBuilder
import toniwar.projects.fakephotoapp.data.network.RetrofitBuilderImpl


@Module
class DataModule {

    @Provides
    fun provideDataBase(context: Context): AppDataBase{
        return AppDataBase.getInstance(context)
    }

    @Provides
    fun provideDAO(db: AppDataBase): ClipArtsDAO{
        return db.clipArtsDao()
    }

    @Provides
    fun provideRetrofit():RetrofitBuilder{
        return RetrofitBuilderImpl()
    }

}
