package com.saran.akkaraviwat.pickuplist.di

import com.saran.akkaraviwat.pickuplist.AppConfigs
import com.saran.akkaraviwat.pickuplist.common.CoroutineDispatcherProvider
import com.saran.akkaraviwat.pickuplist.common.CoroutineDispatcherProviderImpl
import com.saran.akkaraviwat.pickuplist.common.location.DeviceLocationRepository
import com.saran.akkaraviwat.pickuplist.common.location.LocationUtil
import com.saran.akkaraviwat.pickuplist.pickuplist.PickupListViewModel
import com.saran.akkaraviwat.pickuplist.pickuplist.data.PickupHttpInterceptor
import com.saran.akkaraviwat.pickuplist.pickuplist.data.PickupRemoteDataSource
import com.saran.akkaraviwat.pickuplist.pickuplist.data.PickupRepository
import com.saran.akkaraviwat.pickuplist.pickuplist.domain.GetPickupListUseCase
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    private val dataModule = module {

        single<OkHttpClient> {
            OkHttpClient.Builder()
                .addInterceptor(PickupHttpInterceptor)
                .build()
        }
        single {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .baseUrl(AppConfigs.BASE_URL)
                .build()
        }

    }

    private val sharedModule = module {
        single<CoroutineDispatcherProvider> { CoroutineDispatcherProviderImpl()}
        single { LocationUtil(get()) }
        single { DeviceLocationRepository(get(), get()) }
    }

    private val pickupModule = module {
        single { PickupRepository(get()) }
        single {
            get<Retrofit>().create(PickupRemoteDataSource::class.java)
        }
        factory {
            GetPickupListUseCase(get(), get(), get())
        }
        viewModel {
            PickupListViewModel(get(), get())
        }
    }


    val allModules = listOf(
        dataModule,
        sharedModule,
        pickupModule
    )
}
