'use strict';

describe('Controller Tests', function() {

    describe('Entrada Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEntrada, MockTipo, MockBeneficio;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEntrada = jasmine.createSpy('MockEntrada');
            MockTipo = jasmine.createSpy('MockTipo');
            MockBeneficio = jasmine.createSpy('MockBeneficio');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Entrada': MockEntrada,
                'Tipo': MockTipo,
                'Beneficio': MockBeneficio
            };
            createController = function() {
                $injector.get('$controller')("EntradaAngDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coffeeShopApp:entradaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
