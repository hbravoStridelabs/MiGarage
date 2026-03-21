package com.migarage.di;

import com.migarage.data.local.db.MiGarageDatabase;
import com.migarage.data.local.db.dao.VehicleDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideVehicleDaoFactory implements Factory<VehicleDao> {
  private final Provider<MiGarageDatabase> dbProvider;

  public AppModule_ProvideVehicleDaoFactory(Provider<MiGarageDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public VehicleDao get() {
    return provideVehicleDao(dbProvider.get());
  }

  public static AppModule_ProvideVehicleDaoFactory create(Provider<MiGarageDatabase> dbProvider) {
    return new AppModule_ProvideVehicleDaoFactory(dbProvider);
  }

  public static VehicleDao provideVehicleDao(MiGarageDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVehicleDao(db));
  }
}
