package com.migarage.di;

import com.migarage.data.local.db.MiGarageDatabase;
import com.migarage.data.local.db.dao.AlertDao;
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
public final class AppModule_ProvideAlertDaoFactory implements Factory<AlertDao> {
  private final Provider<MiGarageDatabase> dbProvider;

  public AppModule_ProvideAlertDaoFactory(Provider<MiGarageDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AlertDao get() {
    return provideAlertDao(dbProvider.get());
  }

  public static AppModule_ProvideAlertDaoFactory create(Provider<MiGarageDatabase> dbProvider) {
    return new AppModule_ProvideAlertDaoFactory(dbProvider);
  }

  public static AlertDao provideAlertDao(MiGarageDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAlertDao(db));
  }
}
