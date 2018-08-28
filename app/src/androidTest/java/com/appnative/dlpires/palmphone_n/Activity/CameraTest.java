package com.appnative.dlpires.palmphone_n.Activity;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.appnative.dlpires.palmphone_n.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
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

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CameraTest {

    //OBJETO DE ACESSO AOS COMPONENTES DO SOFTWARE
    @Rule
    public ActivityTestRule<LoginPage> mActivityTestRule = new ActivityTestRule<>(LoginPage.class);

    @Test
    public void cameraTest() {

        //AGUARDANDO 6 SEGUNDOS PARA ABRIR O APLICATIVO
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //CLICANDO NA CAIXA DE EMAIL
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.textEmail),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(click());

        //INSERINDO EMAIL
        ViewInteraction appCompatEditText1 = onView(
                allOf(withId(R.id.textEmail),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText1.perform(replaceText("loira@email.com"), closeSoftKeyboard());

        //CLICANDO NA CAIXA DE SENHA
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.textSenha),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        //INSERINDO SENHA
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.textSenha),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("123456"), closeSoftKeyboard());

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

        //PARADA DE 6 SEGUNDOS, PARA ESPERAR O LOGIN
        try {
            Thread.sleep(6000);
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

        //ESPERA DE 3 SEGUNDOS
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //ABRINDO O SPINNER DE DISCIPLINAS
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
                .atPosition(2);
        appCompatCheckedTextView.perform(click());

        //ABRINDO SPINNER DE NUMERO DE AULAS
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
                .atPosition(4);
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

        //ESPERA DE 3 SEGUNDOS
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //CLICANDO NO BOTÃO PARA ABRIR O LEITOR DE CÓDIGO DE BARRAS
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.buttonBarcode),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        floatingActionButton.perform(click());

        //LOOP PARA CADASTRAR LER 10 RAs
        for (int i = 0; i < 10; i++){
            //ESPERA DE 3 SEGUNDOS
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //CONFIRMANDO LEITURA DE CHAMADA
            ViewInteraction appCompatButton4 = onView(
                    allOf(withId(android.R.id.button1), withText("CONFIRMAR"),
                            childAtPosition(
                                    childAtPosition(
                                            withClassName(is("android.widget.ScrollView")),
                                            0),
                                    3)));
            appCompatButton4.perform(scrollTo(), click());

            //ESPERA DE 3 SEGUNDOS
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //SAINDO DA CAMERA
        pressBack();

        //ESPERA DE 3 SEGUNDOS
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //CLICANDO NO BOTÃO FINALIZAR CHAMADA
        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.buttonFinalizar), withText("FINALIZAR CHAMADA"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayoutDigRA),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                2),
                        isDisplayed()));
        appCompatButton5.perform(click());

        //CLICANDO NO BOTÃO CONFIRMAR DO POPUP
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("CONFIRMAR"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton6.perform(scrollTo(), click());

        //ESPERA DE 3 SEGUNDOS
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //VOLTANDO PARA A PÁGINA DE PERFIL
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbarColetor),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        //ESPERA DE 3 SEGUNDOS
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //SINCRONIZANDO NA BASE DE DADOS DO SISTEMA
        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.buttonSync),
                        childAtPosition(
                                allOf(withId(R.id.formSync),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                0),
                        isDisplayed()));
        appCompatButton8.perform(click());

        //CONFIRMANDO SINCRONIZAÇÃO
        ViewInteraction appCompatButton9 = onView(
                allOf(withId(android.R.id.button1), withText("CONFIRMAR"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton9.perform(scrollTo(), click());

        //SAINDO DO SISTEMA
        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.buttonSair), withText("Sair"),
                        childAtPosition(
                                allOf(withId(R.id.formLogout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                0),
                        isDisplayed()));
        appCompatButton7.perform(click());

        //CONFIRMANDO SAIDA
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(android.R.id.button1), withText("CONFIRMAR"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton10.perform(scrollTo(), click());

        //ESPERA DE 3 SEGUNDOS
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
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
