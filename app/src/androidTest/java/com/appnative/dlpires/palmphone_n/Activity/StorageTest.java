package com.appnative.dlpires.palmphone_n.Activity;


import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.appnative.dlpires.palmphone_n.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StorageTest {

    //VARIAVEIS ESTATICAS PCOM AS INFORMAÇÕES DO TREPN PROFILER
    private static final String BASIC_SAMPLE_PACKAGE
            = "com.quicinc.trepn";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "Trepn Profiler";
    //CLASSE PARA UIAUTOMATOR 2
    private UiDevice mDevice;

    //OBJETO DE ACESSO AOS COMPONENTES DO SOFTWARE
    @Rule
    public ActivityTestRule<LoginPage> mActivityTestRule = new ActivityTestRule<>(LoginPage.class);

    //INICIANDO TREPN PROFILER
    @Before
    public void startTrepnProfiler() {
        // INICICIALIZANDO INSTANCIA DO DISPOSITIVO
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // INICIANDO HOME SCREEN
        mDevice.pressHome();

        // ESPERANDO PARA ABRIR O APP
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // INICIANDO O TREPN
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // LIMPANDO OUTRAS INSTANCIAS DO APP
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // ESPERANDO O APP ABRIR
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT);

        try {
            //CLICANDO NO BOTÃO PROFILE APP
            mDevice.findObject(new UiSelector()
                    .text("Profile App")
                    .className("android.widget.TextView")).click();

            //SELECIONANDO O PALMPHONE
            mDevice.findObject(new UiSelector()
                    .text("Palmphone")
                    .className("android.widget.TextView")).click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void storageTest() {

        //ESPERA DE 2 SEGUNDOS
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //INSERINDO O EMAIL
        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.textEmail),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("loira@email.com"));

        //INSERINDO SENHA E FECHANDO TECLADO
        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.textSenha),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("123456"), closeSoftKeyboard());

        //CLICANDO NO BOTÃO ENTRAR
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.buttonLogin), withText("ENTRAR"),
                        childAtPosition(
                                allOf(withId(R.id.loginLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        //ESPERA DE 4 SEGUNDOS = LOGIN
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //CLICANDO NO BOTÃO INICIAR COLETA
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.buttonColeta), withText("Iniciar coleta"),
                        childAtPosition(
                                allOf(withId(R.id.formMenu),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());

        //ESPERA DE 2 SEGUNDOS
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //CLICANDO NO SPINNER DISCIPLINA
        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinnerDisciplina),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        //SELECIONANDO DISCIPLINA
        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                        childAtPosition(
                                withClassName(is("android.widget.FrameLayout")),
                                0)))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());

        //CLICANDO NO SPINNER DE AULAS
        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.spinnerAula),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                2),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        //SELECIONANDO O NUMERO DE AULAS
        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                        childAtPosition(
                                withClassName(is("android.widget.FrameLayout")),
                                0)))
                .atPosition(2);
        appCompatCheckedTextView2.perform(click());

        //CLICANDO NO BOTÃO COLETAR DADOS
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.buttonColetar), withText("Coletar dados"),
                        childAtPosition(
                                allOf(withId(R.id.formMenu),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatButton3.perform(click());

        //REALIZANDO 10 CADASTROS
        for (int i = 0; i < 10; i++){

            //ESPERA DE 2 SEGUNDOS
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //INSERINDO RA
            ViewInteraction appCompatEditText11 = onView(
                    allOf(withId(R.id.textRA),
                            childAtPosition(
                                    childAtPosition(
                                            withClassName(is("android.support.design.widget.TextInputLayout")),
                                            0),
                                    0),
                            isDisplayed()));
            appCompatEditText11.perform(replaceText(Integer.toString(i)));

            //CLICANDO NO BOTÃO SALVAR
            ViewInteraction appCompatButton4 = onView(
                    allOf(withId(R.id.buttonRA), withText("SALVAR"),
                            childAtPosition(
                                    allOf(withId(R.id.linearLayoutDigRA),
                                            childAtPosition(
                                                    withClassName(is("android.widget.LinearLayout")),
                                                    1)),
                                    1),
                            isDisplayed()));
            appCompatButton4.perform(click());
            //ESPERA DE 2 SEGUNDOS
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //CLICANDO EM CONFIRMAR
            ViewInteraction appCompatButton5 = onView(
                    allOf(withId(android.R.id.button1), withText("CONFIRMAR"),
                            /*childAtPosition(
                                    allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                            childAtPosition(
                                                    withClassName(is("android.widget.LinearLayout")),
                                                    3)),
                                    3),
                            isDisplayed()));*/
                            childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
            //appCompatButton5.perform(click());
            appCompatButton5.perform(scrollTo(), click());
        }


        //CLICANDO EM FINALIZAR CHAMADA
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.buttonFinalizar), withText("FINALIZAR CHAMADA"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayoutDigRA),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                2),
                        isDisplayed()));
        appCompatButton6.perform(click());

        //ESPERA DE 2 SEGUNDOS
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //CLICANDO EM CONFIRMAR
        ViewInteraction appCompatButton7 = onView(
                allOf(withId(android.R.id.button1), withText("CONFIRMAR"),
                        /*childAtPosition(
                                allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));*/
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        //appCompatButton7.perform(click());
        appCompatButton7.perform(scrollTo(), click());

        //ESPERA DE 2 SEGUNDOS
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //VOLTANDO NA TELA DE MENU
        /*ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbarColetor),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());*/

        pressBack();

        //ESPERA DE 2 SEGUNDOS
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //SINCRONIZANDO DADOS
        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.buttonSync), withText("Sincronizar"),
                        childAtPosition(
                                allOf(withId(R.id.formSync),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatButton8.perform(click());

        //ESPERA DE 2 SEGUNDOS
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //CONFIRMANDO SYNC
        ViewInteraction appCompatButton9 = onView(
                allOf(withId(android.R.id.button1), withText("CONFIRMAR"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
                        /*childAtPosition(
                                allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));*/
        appCompatButton9.perform(scrollTo(), click());
        //appCompatButton9.perform(click());

        //CLICANDO NO BOTÃO SAIR
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.buttonSair), withText("Sair"),
                        childAtPosition(
                                allOf(withId(R.id.formLogout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                0),
                        isDisplayed()));
        appCompatButton10.perform(click());

        //ESPERA DE 2 SEGUNDOS
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //CONFIRMANDO SAIDA
        ViewInteraction appCompatButton11 = onView(
                allOf(withId(android.R.id.button1), withText("CONFIRMAR"),
                        /*childAtPosition(
                                allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));*/
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        //appCompatButton11.perform(click());
        appCompatButton11.perform(scrollTo(), click());

    }

    @After
    public void stopTrepnProfiler(){
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);

        //ABRINDO APP
        context.startActivity(intent);

        try {
            //CLICANDO NO BOTÃO PROFILE APP
            mDevice.findObject(new UiSelector()
                    .text("Stop Profiling")
                    .className("android.widget.TextView")).click();

            //SELECIONANDO O PALMPHONE
            mDevice.findObject(new UiSelector()
                    .text("Save as .csv")
                    .className("android.widget.Button")).click();

            //SALVANDO ARQUIVO CSV
            mDevice.findObject(new UiSelector()
                    .text("Save")
                    .className("android.widget.Button")).click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
