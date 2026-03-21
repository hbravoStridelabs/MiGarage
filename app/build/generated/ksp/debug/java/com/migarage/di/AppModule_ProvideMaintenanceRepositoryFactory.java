package com.migarage.di;

import com.migarage.data.repository.MaintenanceRepositoryImpl;
import com.migarage.domain.repository.MaintenanceRepository;
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
public final class AppModule_ProvideMaintenanceRepositoryFactory implements Factory<MaintenanceRepository> {
  private final Provider<MaintenanceRepositoryImpl> implProvider;

  public AppModule_ProvideMaintenanceRepositoryFactory(
      Provider<MaintenanceRepositoryImpl> implProvider) {
    this.implProvider = implProvider;
  }

  @Override
  public MaintenanceRepository get() {
    return provideMaintenanceRepository(implProvider.get());
  }

  public static AppModule_ProvideMaintenanceRepositoryFactory create(
      Provider<MaintenanceRepositoryImpl> implProvider) {
    return new AppModule_ProvideMaintenanceRepositoryFactory(implProvider);
  }

  public static MaintenanceRepository provideMaintenanceRepository(MaintenanceRepositoryImpl impl) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideMaintenanceRepository(impl));
  }
}
