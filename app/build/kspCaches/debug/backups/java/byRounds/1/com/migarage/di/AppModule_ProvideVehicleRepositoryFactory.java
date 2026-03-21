package com.migarage.di;

import com.migarage.data.repository.VehicleRepositoryImpl;
import com.migarage.domain.repository.VehicleRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class AppModule_ProvideVehicleRepositoryFactory implements Factory<VehicleRepository> {
  private final Provider<VehicleRepositoryImpl> implProvider;

  public AppModule_ProvideVehicleRepositoryFactory(Provider<VehicleRepositoryImpl> implProvider) {
    this.implProvider = implProvider;
  }

  @Override
  public VehicleRepository get() {
    return provideVehicleRepository(implProvider.get());
  }

  public static AppModule_ProvideVehicleRepositoryFactory create(
      Provider<VehicleRepositoryImpl> implProvider) {
    return new AppModule_ProvideVehicleRepositoryFactory(implProvider);
  }

  public static VehicleRepository provideVehicleRepository(VehicleRepositoryImpl impl) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVehicleRepository(impl));
  }
}
