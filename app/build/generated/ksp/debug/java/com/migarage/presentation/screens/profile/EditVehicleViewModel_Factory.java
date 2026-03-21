package com.migarage.presentation.screens.profile;

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
public final class EditVehicleViewModel_Factory implements Factory<EditVehicleViewModel> {
  private final Provider<VehicleRepository> vehicleRepositoryProvider;

  public EditVehicleViewModel_Factory(Provider<VehicleRepository> vehicleRepositoryProvider) {
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
  }

  @Override
  public EditVehicleViewModel get() {
    return newInstance(vehicleRepositoryProvider.get());
  }

  public static EditVehicleViewModel_Factory create(
      Provider<VehicleRepository> vehicleRepositoryProvider) {
    return new EditVehicleViewModel_Factory(vehicleRepositoryProvider);
  }

  public static EditVehicleViewModel newInstance(VehicleRepository vehicleRepository) {
    return new EditVehicleViewModel(vehicleRepository);
  }
}
