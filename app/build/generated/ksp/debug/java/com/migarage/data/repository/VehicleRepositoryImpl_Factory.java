package com.migarage.data.repository;

import com.migarage.data.local.db.dao.VehicleDao;
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
public final class VehicleRepositoryImpl_Factory implements Factory<VehicleRepositoryImpl> {
  private final Provider<VehicleDao> vehicleDaoProvider;

  public VehicleRepositoryImpl_Factory(Provider<VehicleDao> vehicleDaoProvider) {
    this.vehicleDaoProvider = vehicleDaoProvider;
  }

  @Override
  public VehicleRepositoryImpl get() {
    return newInstance(vehicleDaoProvider.get());
  }

  public static VehicleRepositoryImpl_Factory create(Provider<VehicleDao> vehicleDaoProvider) {
    return new VehicleRepositoryImpl_Factory(vehicleDaoProvider);
  }

  public static VehicleRepositoryImpl newInstance(VehicleDao vehicleDao) {
    return new VehicleRepositoryImpl(vehicleDao);
  }
}
