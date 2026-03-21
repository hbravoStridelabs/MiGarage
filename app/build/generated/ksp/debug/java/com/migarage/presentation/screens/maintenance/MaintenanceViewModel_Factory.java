package com.migarage.presentation.screens.maintenance;

import com.migarage.domain.repository.MaintenanceRepository;
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
public final class MaintenanceViewModel_Factory implements Factory<MaintenanceViewModel> {
  private final Provider<MaintenanceRepository> repositoryProvider;

  public MaintenanceViewModel_Factory(Provider<MaintenanceRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public MaintenanceViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static MaintenanceViewModel_Factory create(
      Provider<MaintenanceRepository> repositoryProvider) {
    return new MaintenanceViewModel_Factory(repositoryProvider);
  }

  public static MaintenanceViewModel newInstance(MaintenanceRepository repository) {
    return new MaintenanceViewModel(repository);
  }
}
