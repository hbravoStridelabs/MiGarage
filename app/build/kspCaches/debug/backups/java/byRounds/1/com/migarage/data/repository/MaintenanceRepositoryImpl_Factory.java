package com.migarage.data.repository;

import com.migarage.data.local.db.dao.MaintenanceRecordDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class MaintenanceRepositoryImpl_Factory implements Factory<MaintenanceRepositoryImpl> {
  private final Provider<MaintenanceRecordDao> maintenanceRecordDaoProvider;

  public MaintenanceRepositoryImpl_Factory(
      Provider<MaintenanceRecordDao> maintenanceRecordDaoProvider) {
    this.maintenanceRecordDaoProvider = maintenanceRecordDaoProvider;
  }

  @Override
  public MaintenanceRepositoryImpl get() {
    return newInstance(maintenanceRecordDaoProvider.get());
  }

  public static MaintenanceRepositoryImpl_Factory create(
      Provider<MaintenanceRecordDao> maintenanceRecordDaoProvider) {
    return new MaintenanceRepositoryImpl_Factory(maintenanceRecordDaoProvider);
  }

  public static MaintenanceRepositoryImpl newInstance(MaintenanceRecordDao maintenanceRecordDao) {
    return new MaintenanceRepositoryImpl(maintenanceRecordDao);
  }
}
