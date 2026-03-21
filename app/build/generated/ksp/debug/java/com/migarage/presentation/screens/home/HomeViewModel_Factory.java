package com.migarage.presentation.screens.home;

import com.migarage.domain.repository.AlertRepository;
import com.migarage.domain.repository.DocumentRepository;
import com.migarage.domain.repository.MaintenanceRepository;
import com.migarage.domain.repository.VehicleRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<VehicleRepository> vehicleRepositoryProvider;

  private final Provider<DocumentRepository> documentRepositoryProvider;

  private final Provider<MaintenanceRepository> maintenanceRepositoryProvider;

  private final Provider<AlertRepository> alertRepositoryProvider;

  public HomeViewModel_Factory(Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<DocumentRepository> documentRepositoryProvider,
      Provider<MaintenanceRepository> maintenanceRepositoryProvider,
      Provider<AlertRepository> alertRepositoryProvider) {
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
    this.documentRepositoryProvider = documentRepositoryProvider;
    this.maintenanceRepositoryProvider = maintenanceRepositoryProvider;
    this.alertRepositoryProvider = alertRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(vehicleRepositoryProvider.get(), documentRepositoryProvider.get(), maintenanceRepositoryProvider.get(), alertRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<VehicleRepository> vehicleRepositoryProvider,
      Provider<DocumentRepository> documentRepositoryProvider,
      Provider<MaintenanceRepository> maintenanceRepositoryProvider,
      Provider<AlertRepository> alertRepositoryProvider) {
    return new HomeViewModel_Factory(vehicleRepositoryProvider, documentRepositoryProvider, maintenanceRepositoryProvider, alertRepositoryProvider);
  }

  public static HomeViewModel newInstance(VehicleRepository vehicleRepository,
      DocumentRepository documentRepository, MaintenanceRepository maintenanceRepository,
      AlertRepository alertRepository) {
    return new HomeViewModel(vehicleRepository, documentRepository, maintenanceRepository, alertRepository);
  }
}
