package com.migarage.di;

import com.migarage.data.local.db.MiGarageDatabase;
import com.migarage.data.local.db.dao.MaintenanceRecordDao;
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
public final class AppModule_ProvideMaintenanceRecordDaoFactory implements Factory<MaintenanceRecordDao> {
  private final Provider<MiGarageDatabase> dbProvider;

  public AppModule_ProvideMaintenanceRecordDaoFactory(Provider<MiGarageDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public MaintenanceRecordDao get() {
    return provideMaintenanceRecordDao(dbProvider.get());
  }

  public static AppModule_ProvideMaintenanceRecordDaoFactory create(
      Provider<MiGarageDatabase> dbProvider) {
    return new AppModule_ProvideMaintenanceRecordDaoFactory(dbProvider);
  }

  public static MaintenanceRecordDao provideMaintenanceRecordDao(MiGarageDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideMaintenanceRecordDao(db));
  }
}
